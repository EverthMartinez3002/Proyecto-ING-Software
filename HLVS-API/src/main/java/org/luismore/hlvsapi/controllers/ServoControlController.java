package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.websocket.ServoWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servo")
public class ServoControlController {

    @Autowired
    private ServoWebSocketHandler webSocketHandler;

    @PostMapping("/move")
    public String moveServo() {
        try {
            webSocketHandler.sendCommandToAllSessions("move 90");
            Thread.sleep(5000);
            webSocketHandler.sendCommandToAllSessions("move 0");
            return "Servo moved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error moving servo";
        }
    }
}
