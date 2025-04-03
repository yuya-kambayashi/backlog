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

        System.setProperty("accessKey", dotenv.get("accessKey"));
        System.setProperty("secretKey", dotenv.get("secretKey"));
        System.setProperty("region", dotenv.get("region"));
        System.setProperty("s3_bucket", dotenv.get("s3_bucket"));
        System.setProperty("cloudfront_destribution_domain_name", dotenv.get("cloudfront_destribution_domain_name"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BacklogApplication.class, args);
    }

}
