package ebd.api_ebd;

import org.springframework.boot.SpringApplication;

public class TestApiEbdApplication {

	public static void main(String[] args) {
		SpringApplication.from(ApiEbdApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
