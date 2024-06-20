package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestService {
    List<Request> getRequestsByHomeIdAndStatus(UUID homeId, String status);
    Request createSingleRequest(CreateSingleRequestDTO createRequestDTO, User user);
    List<Request> createMultipleRequests(CreateMultipleRequestDTO createRequestDTO, User user);
    List<Request> getRequestHistoryByUserHouse(User user);
    Optional<Request> getRequestById(UUID requestId);
    void save(Request request);
    void updateRequestState(Request request, String stateId);
    RequestDTO convertToDTO(Request request); // Añadir este método
}
