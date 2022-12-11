package br.com.helpconnect.chatConnect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import br.com.helpconnect.chatConnect.storage.StorageProperties;
import br.com.helpconnect.chatConnect.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
// public class ChatConnectApplication extends SpringBootServletInitializer {
public class ChatConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatConnectApplication.class, args);
	}
	
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ChatConnectApplication.class);
	}*/
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
