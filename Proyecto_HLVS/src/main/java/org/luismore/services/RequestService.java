package org.luismore.services;


import java.util.List;



public interface RequestService {
    List<Request> findPendingRequestsByHomeId(int homeId);
    Request createRequest(RequestDTO requestDTO, boolean isAdminOrMainResident);
    List<Request> findAll();
    Request updateRequestStatus(UpdateRequestDTO updateRequestDTO);
}