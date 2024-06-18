//package org.luismore.hlvsapi.domain.dtos;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import org.luismore.hlvsapi.domain.entities.Request;
//
//import java.util.UUID;
//
//@Data
//public class CreateQrDTO {
//    @NotBlank
//    private String token;
//    @NotNull
//    private Integer duration;
//    @NotNull
//    private UUID requestId;
//
//    public Request getRequest() {
//        Request request = new Request();
//        request.setId(requestId);
//        return request;
//    }
//}

package org.luismore.hlvsapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateQrDTO {
    @NotBlank
    private String token;
    @NotNull
    private Integer duration;
    @NotNull
    private UUID requestId;
}

