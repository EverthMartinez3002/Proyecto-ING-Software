package org.luismore.services.impls;

import org.luismore.services.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> findPendingRequestsByHomeId(int homeId) {
        return requestRepository.findByHomeIdAndState(homeId, "pending");
    }

    @Override
    public Request createRequest(RequestDTO requestDTO, boolean isAdminOrMainResident) {
        Request request = new Request();
        request.setDui(requestDTO.getDui());
        request.setHomeId(requestDTO.getHomeId());
        request.setEntryDate(requestDTO.getEntryDate());
        request.setEntryTime(requestDTO.getEntryTime());
        request.setState(isAdminOrMainResident ? "approved" : "pending");
        return requestRepository.save(request);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request updateRequestStatus(UpdateRequestDTO updateRequestDTO) {
        Request request = requestRepository.findById(updateRequestDTO.getRequestId()).orElseThrow();
        request.setState(updateRequestDTO.getState());
        return requestRepository.save(request);
    }
}