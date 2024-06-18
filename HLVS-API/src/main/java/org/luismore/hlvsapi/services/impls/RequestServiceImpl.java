package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.CreateRequestDTO;
import org.luismore.hlvsapi.domain.entities.Request;
import org.luismore.hlvsapi.domain.entities.State;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.RequestRepository;
import org.luismore.hlvsapi.repositories.StateRepository;
import org.luismore.hlvsapi.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final StateRepository stateRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, StateRepository stateRepository) {
        this.requestRepository = requestRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public List<Request> getRequestsByHomeIdAndStatus(UUID homeId, String status) {
        return requestRepository.findByHouseIdAndState(homeId, status);
    }

    @Override
    @Transactional
    public Request createRequest(CreateRequestDTO createRequestDTO, User user) {
        Request request = new Request();
        request.setDUI(createRequestDTO.getDui());
        request.setEntryDate(createRequestDTO.getEntryDate());
        request.setEntryTime(createRequestDTO.getEntryTime());
        request.setLimitTime(createRequestDTO.getLimitTime());
        State state = stateRepository.findById(createRequestDTO.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid state id"));
        request.setState(state);
        request.setHouse(user.getHouse());
        request.setVisitor(user);
        return requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestHistoryByUserHouse(User user) {
        return requestRepository.findByHouseId(user.getHouse().getId());
    }

    @Override
    public Optional<Request> getRequestById(UUID requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }
}
