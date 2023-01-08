package br.com.helpconnect.chatConnect.websocketprocessor.process;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedProcess {
    private LocalDateTime startAt;
    
	/*public CreatedProcess(LocalDateTime startAt) {
		this.startAt = startAt;
	}*/

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}
    
}

