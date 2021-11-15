package pl.czupryn.rob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RobApplication {

	// jeżeli nie masz na kompei DB mySql to ściągnij i ustaw haslo w registerControler na
	// swoje bo inaczej nie będzie działać

	public static void main(String[] args) {
		SpringApplication.run(RobApplication.class, args);
	}

}
