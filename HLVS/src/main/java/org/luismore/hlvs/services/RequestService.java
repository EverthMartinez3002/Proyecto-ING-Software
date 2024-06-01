package org.luismore.hlvs.services;

import org.luismore.hlvs.dtos.RequestDto;
import org.luismore.hlvs.entities.Request;

import java.util.List;

public interface RequestService {
    List<Request> getRequestsByHomeId(Long homeId);
    Request createRequest(RequestDto requestDto, String role);
    List<Request> getRequestHistory();
    Request updateRequest(Long requestId, RequestDto requestDto, String role);
}
