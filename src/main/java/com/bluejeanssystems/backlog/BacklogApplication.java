package com.bluejeanssystems.backlog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BacklogApplication {

    static {
        // `.env` を読み込む
        Dotenv dotenv = Dotenv.load();
        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BacklogApplication.class, args);
    }

}
