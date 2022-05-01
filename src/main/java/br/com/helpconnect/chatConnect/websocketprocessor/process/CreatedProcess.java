package br.com.helpconnect.chatConnect.websocketprocessor.process;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedProcess {
    private LocalDateTime startAt;
}

