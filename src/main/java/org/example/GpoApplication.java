package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GpoApplication implements CommandLineRunner {

    @Autowired
    private GraphQLClientExample graphQLClientExample;

    public static void main(String[] args) {
        SpringApplication.run(GpoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        graphQLClientExample.runExample();
    }
}
