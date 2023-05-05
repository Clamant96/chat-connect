package br.com.helpconnect.chatConnect.websocketprocessor.process.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessorService {
	
    private SimpMessagingTemplate template;

    @Autowired
    public ProcessorService(SimpMessagingTemplate template) {
        this.template = template;
        
    }

    @Async
    public void execute() {
        try {
            Thread.sleep(1000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(1));
            /*Thread.sleep(2000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(2));
            Thread.sleep(2000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(3));*/
        } catch (InterruptedException e) {
            System.out.println("Erro durante o procesamento."+ e);
        }
    }

    private String gerarMensagem(int etapa) {
        return String.format("Executada a etapa %s às %s", etapa,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
