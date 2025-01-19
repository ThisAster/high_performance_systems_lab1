package com.example.clinic.mail.controller;

import com.example.clinic.mail.dto.EmailDto;
import com.example.clinic.mail.service.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class Controller {

    private final Sender sender;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody EmailDto dto) {
        sender.sendEmail(dto);
        return ResponseEntity.ok().build();
    }
}
