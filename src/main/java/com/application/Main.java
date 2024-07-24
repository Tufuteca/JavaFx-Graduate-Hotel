package com.application;

import com.application.controller.output.ui.AuthorizationView;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Main extends Application {


    @Getter
    private static ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(Main.class);
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AuthorizationView.loadView(primaryStage);
    }


}
