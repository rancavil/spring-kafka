package cl.microservices.kafka;

import cl.microservices.data.Temperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

	@Autowired
	private KafkaTemplate<String, Temperature> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {
		Random random = new Random();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

		for (int i = 0; i < 1000; i++) {
			int id = i+1;
			String now = LocalDateTime.now().format(formatter);
			float degrees = (float) random.nextInt(30);
			logger.info("Sending DATA to TOPIC <</data>> Message : DATA ID : <<"+i+">>");

			this.kafkaTemplate.send("data", new Temperature(id, now,degrees));
		}
	}
}
