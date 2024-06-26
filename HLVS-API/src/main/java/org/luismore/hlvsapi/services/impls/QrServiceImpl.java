package org.luismore.hlvsapi.services.impls;

import org.luismore.hlvsapi.domain.dtos.CreateQrDTO;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.domain.entities.QR;
import org.luismore.hlvsapi.domain.entities.QRLimit;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.Tablet;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.repositories.QrLimitRepository;
import org.luismore.hlvsapi.repositories.QrRepository;
import org.luismore.hlvsapi.repositories.RequestRepository;
import org.luismore.hlvsapi.repositories.TabletRepository;
import org.luismore.hlvsapi.services.QrService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrServiceImpl implements QrService {

    private final QrRepository qrRepository;
    private final QrLimitRepository qrLimitRepository;
    private final RequestRepository requestRepository;
    private final TabletRepository tabletRepository;
    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;

    public QrServiceImpl(QrRepository qrRepository, QrLimitRepository qrLimitRepository, RequestRepository requestRepository, TabletRepository tabletRepository, EntryRepository entryRepository, EntryTypeRepository entryTypeRepository) {
        this.qrRepository = qrRepository;
        this.qrLimitRepository = qrLimitRepository;
        this.requestRepository = requestRepository;
        this.tabletRepository = tabletRepository;
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
    }

    @Override
    public QR generateQrToken(CreateQrDTO createQrDTO) {
        QR qr = new QR();
        qr.setToken(createQrDTO.getToken());
        qr.setUsed(false);

        QRLimit qrLimit = qrLimitRepository.findById(1).orElseThrow(() -> new RuntimeException("QR Limit not found"));
        qr.setQrLimit(qrLimit);

        Request request = requestRepository.findById(createQrDTO.getRequestId()).orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"APPR".equals(request.getState().getId())) {
            throw new RuntimeException("Request is not approved");
        }

        qr.setRequest(request);

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        if (today.isEqual(request.getEntryDate()) && now.isAfter(request.getBeforeTime()) && now.isBefore(request.getAfterTime())) {
            qr.setExpDate(today);
            qr.setExpTime(now.plusMinutes(qrLimit.getMinutesDuration()));
            qr.setUser(request.getVisitor());
            return qrRepository.save(qr);
        } else {
            throw new RuntimeException("QR cannot be generated outside the allowed time range");
        }
    }

    @Override
    public QR scanQrToken(String token, String serialNumber) {
        Optional<QR> qrOptional = qrRepository.findByToken(token);
        if (qrOptional.isPresent()) {
            QR qr = qrOptional.get();
            
            if (qr.getUsed()) {
                throw new RuntimeException("QR code has already been used");
            }

            if (!qr.getUsed() && qr.getExpDate().isEqual(LocalDate.now()) && qr.getExpTime().isAfter(LocalTime.now())) {
                qr.setUsed(true);

                Tablet tablet = tabletRepository.findBySerialNumber(serialNumber).orElseThrow(() -> new RuntimeException("Tablet not found"));
                Entry entry = new Entry();
                entry.setDate(LocalDate.now());
                entry.setEntryTime(LocalTime.now());
                entry.setUser(qr.getUser());
                entry.setHouse(qr.getRequest().getHouse());
                entry.setDui(qr.getRequest().getDUI());

                EntryType entryType = entryTypeRepository.findById(tablet.getLocation().equalsIgnoreCase("Vehicle") ? "VEHI" : "PEDE")
                        .orElseThrow(() -> new RuntimeException("Entry type not found"));
                entry.setEntryType(entryType);

                entry.setComment(String.format("Usuario %s, entro a las %s el dia %s por la entrada %s",
                        qr.getUser().getName(),
                        entry.getEntryTime(),
                        entry.getDate(),
                        entryType.getId().equals("VEHI") ? "vehicular" : "peatonal"));

                entryRepository.save(entry);
                qrRepository.save(qr);
                return qr;
            }
        }
        return null;
    }

    @Override
    public void updateQrExpiration(int duration) {
        QRLimit qrLimit = qrLimitRepository.findById(1).orElseThrow(() -> new RuntimeException("QR Limit not found"));
        qrLimit.setMinutesDuration(duration);
        qrLimitRepository.save(qrLimit);
    }
}
