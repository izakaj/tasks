package com.crud.tasks;

import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//public class TasksApplication extends SpringBootServletInitializer {
public class TasksApplication {



	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}
    /*
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(TasksApplication.class);
    } */

}
