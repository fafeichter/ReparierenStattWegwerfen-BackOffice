package at.reparierenstattwegwerfen.backoffice;

import org.springframework.boot.SpringApplication;

public class TestBackofficeApplication {

    public static void main(String[] args) {
        SpringApplication.from(BackofficeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
