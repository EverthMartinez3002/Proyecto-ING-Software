package org.luismore.hlvs.services.impls;

import org.luismore.hlvs.dtos.RequestDto;
import org.luismore.hlvs.entities.Request;
import org.luismore.hlvs.repositories.RequestRepository;
import org.luismore.hlvs.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> getRequestsByHomeId(Long homeId) {
        return requestRepository.findByHomeIdAndState(homeId, "Pending");
    }

    @Override
    public Request createRequest(RequestDto requestDto, String role) {
        Request request = new Request();
        request.setDui(requestDto.getDui());
        request.setHomeId(requestDto.getHomeId());
        request.setEntryDate(requestDto.getEntryDate());
        request.setEntryTime(requestDto.getEntryTime());
        request.setState(role.equals("Main Resident") || role.equals("Admin") ? "Approved" : "Pending");
        return requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestHistory() {
        return requestRepository.findAll();
    }

    @Override
    public Request updateRequest(Long requestId, RequestDto requestDto, String role) {
        if (!role.equals("Main Resident") && !role.equals("Admin")) {
            throw new UnauthorizedException("You do not have permission to update this request");
        }
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setState(requestDto.getState());
        return requestRepository.save(request);
    }
}
