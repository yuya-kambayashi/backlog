package com.bluejeanssystems.backlog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;


@SpringBootApplication
public class BacklogApplication {

    static {
        // `.env` を読み込む
        Dotenv dotenv = Dotenv.load();
        System.setProperty("EMAIL_USERNAME", Objects.requireNonNull(dotenv.get("EMAIL_USERNAME")));
        System.setProperty("EMAIL_PASSWORD", Objects.requireNonNull(dotenv.get("EMAIL_PASSWORD")));

        System.setProperty("accessKey", Objects.requireNonNull(dotenv.get("accessKey")));
        System.setProperty("secretKey", Objects.requireNonNull(dotenv.get("secretKey")));
        System.setProperty("region", Objects.requireNonNull(dotenv.get("region")));
        System.setProperty("s3_bucket", Objects.requireNonNull(dotenv.get("s3_bucket")));
        System.setProperty("cloudfront_distribution_domain_name", Objects.requireNonNull(dotenv.get("cloudfront_distribution_domain_name")));
    }

    public static void main(String[] args) {
        SpringApplication.run(BacklogApplication.class, args);
    }

}
