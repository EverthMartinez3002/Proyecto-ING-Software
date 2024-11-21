package org.luismore.hlvsapi.services.impls;

import io.jsonwebtoken.io.IOException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.ZipFile;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.domain.entities.Tablet;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.repositories.TabletRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipException;

import java.io.FileWriter;
import java.util.List;




@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;
    private final UserRepository userRepository;
    private final TabletRepository tabletRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository, UserRepository userRepository, TabletRepository tabletRepository) {
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
        this.userRepository = userRepository;
        this.tabletRepository = tabletRepository;
    }

    @Override
    public Page<EntryWithHouseNumberDTO> getAllEntries(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return entryRepository.findAllByEntryType_Id(filter, pageable).map(this::toEntryWithHouseNumberDTO);
        } else {
            return entryRepository.findAll(pageable).map(this::toEntryWithHouseNumberDTO);
        }
    }

    @Override
    public Page<EntryDTO> getEntriesByHouse(UUID houseId, Pageable pageable) {
        return entryRepository.findAllByHouse_Id(houseId, pageable).map(this::toDTO);
    }

    @Override
    public Page<EntryDTO> getEntriesByUser(UUID userId, Pageable pageable) {
        return entryRepository.findAllByUser_Id(userId, pageable).map(this::toDTO);
    }

    @Override
    public EntryDTO registerAnonymousEntry(EntryAnonymousDTO info, String email) {
        Entry entry = new Entry();
        entry.setEntryTime(LocalTime.now());
        entry.setDate(LocalDate.now());

        EntryType entryType = entryTypeRepository.findTypeByType("anonymous");
        entry.setEntryType(entryType);

        entry.setComment(info.getComment());
        entry.setHeadline(info.getHeadline());

        entryRepository.save(entry);

        if (shouldOpenServoP(email)) {
            sendWebSocketCommand("http://localhost:8080/api/servo/moveP");
        }

        return null;
    }

    @Override
    public EntryTypeCountDTO getVehicleAndPedestrianCounts() {
        int vehicleCount = entryRepository.countByEntryType_Id("VEHI");
        int pedestrianCount = entryRepository.countByEntryType_Id("PEDE");

        EntryTypeCountDTO dto = new EntryTypeCountDTO();
        dto.setData(Arrays.asList(vehicleCount, pedestrianCount));
        return dto;
    }

    @Override
    public EntryTypeCountDTO getResidentVisitorAnonymousCounts() {
        int residentCount = entryRepository.countByHouseIsNullAndEntryType_IdNot("ANON");
        int visitorCount = entryRepository.countByHouseIsNotNull();
        int anonymousCount = entryRepository.countByEntryType_Id("ANON");

        EntryTypeCountDTO dto = new EntryTypeCountDTO();
        dto.setData(Arrays.asList(residentCount, visitorCount, anonymousCount));
        return dto;
    }

    @Override
    public CombinedEntryTypeCountDTO getCombinedCounts() {
        EntryTypeCountDTO graph1Counts = getVehicleAndPedestrianCounts();
        EntryTypeCountDTO graph2Counts = getResidentVisitorAnonymousCounts();
        EntryWeekdayCountDTO graph3Counts = getWeekdayCounts();

        CombinedEntryTypeCountDTO combinedCounts = new CombinedEntryTypeCountDTO();
        combinedCounts.setGraph1Counts(graph1Counts.getData()); //PASTEL
        combinedCounts.setGraph2Counts(graph2Counts.getData()); //
        combinedCounts.setGraph3Counts(graph3Counts.getData()); //DIA DE LA SEMANA

        return combinedCounts;
    }

    @Override
    public EntryWeekdayCountDTO getWeekdayCounts() {
        LocalDate now = LocalDate.now();
        List<Entry> entries = entryRepository.findAll().stream()
                .filter(entry -> entry.getDate() != null && entry.getDate().getMonth() == now.getMonth())
                .collect(Collectors.toList());

        Map<DayOfWeek, Long> counts = entries.stream()
                .collect(Collectors.groupingBy(entry -> entry.getDate().getDayOfWeek(), Collectors.counting()));

        EntryWeekdayCountDTO dto = new EntryWeekdayCountDTO();
        dto.setData(Arrays.asList(
                counts.getOrDefault(DayOfWeek.MONDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.TUESDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.WEDNESDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.THURSDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.FRIDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.SATURDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.SUNDAY, 0L).intValue()
        ));

        return dto;
    }

    private EntryDTO toDTO(Entry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setUserName(entry.getUser() != null ? entry.getUser().getName() : null);
        dto.setHouseAddress(entry.getHouse() != null ? entry.getHouse().getAddress() : null);
        dto.setDui(entry.getDui());
        dto.setComment(entry.getComment());
        dto.setEntryType(entry.getEntryType() != null ? entry.getEntryType().getType() : null);
        dto.setHeadline(entry.getHeadline());
        return dto;
    }

    private EntryWithHouseNumberDTO toEntryWithHouseNumberDTO(Entry entry) {
        EntryWithHouseNumberDTO dto = new EntryWithHouseNumberDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setUserName(entry.getUser() != null ? entry.getUser().getName() : null);
        dto.setHouseAddress(entry.getHouse() != null ? entry.getHouse().getAddress() : null);
        dto.setHouseNumber(entry.getHouse() != null ? entry.getHouse().getHouseNumber() : null);
        dto.setDui(entry.getDui());
        dto.setComment(entry.getComment());
        dto.setEntryType(entry.getEntryType() != null ? entry.getEntryType().getType() : null);
        dto.setHeadline(entry.getHeadline());

        if (dto.getHouseAddress() == null || dto.getHouseNumber() == null) {
            if (entry.getUser() != null) {
                User user = userRepository.findById(entry.getUser().getId())
                        .orElse(null);
                if (user != null && user.getHouse() != null) {
                    dto.setHouseAddress(user.getHouse().getAddress());
                    dto.setHouseNumber(user.getHouse().getHouseNumber());
                }
            }
        }

        return dto;
    }

    @Override
    public boolean shouldOpenServoP(String email) {
        Tablet tablet = tabletRepository.findBySecurityGuard_Email(email).orElseThrow(() -> new RuntimeException("Tablet not found"));
        return "Pedestrian".equalsIgnoreCase(tablet.getLocation());
    }

    @Override
    public void sendWebSocketCommand(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Servo activation response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    @Override
//    public ByteArrayResource exportEntriesToCsv(LocalDate startDate, LocalDate endDate, String entryType) {
//        List<Entry> entries = entryRepository.findByDateRangeAndType(startDate, endDate, entryType);
//
//        StringBuilder csvData = new StringBuilder();
//        csvData.append("Fecha,Hora,Tipo de Acceso,Residente Asociado,Estado\n");
//
//        for (Entry entry : entries) {
//            csvData.append(String.format("%s,%s,%s,%s,%s\n",
//                    entry.getDate(),
//                    entry.getEntryTime(),
//                    entry.getEntryType().getType(),
//                    entry.getHouse() != null ? entry.getHouse().getLeader().getName() : "N/A",
//                    entry.getComment() != null ? entry.getComment() : "N/A"
//            ));
//        }
//
//        return new ByteArrayResource(csvData.toString().getBytes(StandardCharsets.UTF_8));
//    }


//
//    @Override
//    public ByteArrayResource exportEntriesToSecureZip(LocalDate startDate, LocalDate endDate, String entryType, String password) {
//        try {
//            // Generar archivo CSV temporal
//            File tempCsvFile = File.createTempFile("entries_report", ".csv");
//            try (FileWriter writer = new FileWriter(tempCsvFile)) {
//                writer.append("Fecha,Hora,Tipo de Acceso,Residente Asociado,Estado\n");
//
//                List<Entry> entries = entryRepository.findByDateRangeAndType(startDate, endDate, entryType);
//                for (Entry entry : entries) {
//                    writer.append(String.format(
//                            "%s,%s,%s,%s,%s\n",
//                            entry.getDate(),
//                            entry.getEntryTime(),
//                            entry.getEntryType().getType(),
//                            entry.getHouse() != null ? entry.getHouse().getLeader().getName() : "N/A",
//                            entry.getComment() != null ? entry.getComment() : "N/A"
//                    ));
//                }
//            }
//
//            // Crear archivo ZIP temporal
//            File tempZipFile = File.createTempFile("entries_report", ".zip");
//
//            // Crear el archivo ZIP con zip4j
//            ZipFile zipFile = new ZipFile(tempZipFile);
//            if (password != null && !password.isEmpty()) {
//                zipFile.setPassword(password.toCharArray());
//            }
//
//            zipFile.addFile(tempCsvFile); // Añadir el archivo CSV al ZIP
//
//            // Convertir el archivo ZIP a ByteArrayResource
//            ByteArrayResource zipResource = new ByteArrayResource(Files.readAllBytes(tempZipFile.toPath()));
//
//            // Eliminar archivos temporales
//            tempCsvFile.delete();
//            tempZipFile.delete();
//
//            return zipResource;
//
//        } catch (java.io.IOException e) {
//            throw new RuntimeException("Error al generar el archivo ZIP", e);
//        }
//    }

//    @Override
//    public ByteArrayResource exportEntriesToSecureZip(LocalDate startDate, LocalDate endDate, String entryType) {
//        try {
//            // Define la contraseña fija
//            final String fixedPassword = "AdminSecurePass123";
//
//            // Generar archivo CSV temporal
//            File tempCsvFile = File.createTempFile("entries_report", ".csv");
//            try (FileWriter writer = new FileWriter(tempCsvFile)) {
//                writer.append("Fecha,Hora,Tipo de Acceso,Residente Asociado,Estado\n");
//
//                List<Entry> entries = entryRepository.findByDateRangeAndType(startDate, endDate, entryType);
//                for (Entry entry : entries) {
//                    writer.append(String.format(
//                            "%s,%s,%s,%s,%s\n",
//                            entry.getDate(),
//                            entry.getEntryTime(),
//                            entry.getEntryType().getType(),
//                            entry.getHouse() != null ? entry.getHouse().getLeader().getName() : "N/A",
//                            entry.getComment() != null ? entry.getComment() : "N/A"
//                    ));
//                }
//            }
//
//            // Crear archivo ZIP temporal
//            File tempZipFile = File.createTempFile("entries_report", ".zip");
//
//            // Crear el archivo ZIP con zip4j y proteger con contraseña
//            ZipFile zipFile = new ZipFile(tempZipFile);
//            zipFile.setPassword(fixedPassword.toCharArray());
//            zipFile.addFile(tempCsvFile);
//
//            // Convertir el archivo ZIP a ByteArrayResource
//            ByteArrayResource zipResource = new ByteArrayResource(Files.readAllBytes(tempZipFile.toPath()));
//
//            // Eliminar archivos temporales
//            tempCsvFile.delete();
//            tempZipFile.delete();
//
//            return zipResource;
//
//        } catch (java.io.IOException e) {
//            throw new RuntimeException("Error al generar el archivo ZIP", e);
//        }
//    }



    @Override
    public ByteArrayResource exportEntriesToCsv(LocalDate startDate, LocalDate endDate, String entryType) {
        try {
            // Crear contenido CSV en memoria
            StringBuilder csvData = new StringBuilder();
            csvData.append("Fecha,Hora,Tipo de Acceso,Residente Asociado,Estado\n");

            // Obtener entradas desde la base de datos
            List<Entry> entries = entryRepository.findByDateRangeAndType(startDate, endDate, entryType);
            for (Entry entry : entries) {
                csvData.append(String.format(
                        "%s,%s,%s,%s,%s\n",
                        entry.getDate(),
                        entry.getEntryTime(),
                        entry.getEntryType() != null ? entry.getEntryType().getType() : "N/A",
                        entry.getHouse() != null && entry.getHouse().getLeader() != null ? entry.getHouse().getLeader().getName() : "N/A",
                        entry.getComment() != null ? entry.getComment() : "N/A"
                ));
            }

            // Convertir el contenido del CSV a un recurso
            return new ByteArrayResource(csvData.toString().getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el archivo CSV", e);
        }
    }








}
