package edu.wgu.d387_sample_code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class D387SampleCodeApplication {
	static ExecutorService messageExecutor = newFixedThreadPool(5);

	// List to store welcome messages
	private static final List<String> messages = new ArrayList<>();

	// List to store times and dates
	private static final List<String> presentationTimes = new ArrayList<>();



	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);
		messageExecutor.execute(() -> {
			try {
				Properties prop = new Properties();
				InputStream stream = new ClassPathResource("translation_en_US.properties").getInputStream();
				prop.load(stream);
				String welcome = prop.getProperty("welcome");
				messages.add(welcome);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		messageExecutor.execute(() -> {
			try {
				Properties prop = new Properties();
				InputStream stream = new ClassPathResource("translation_fr_CA.properties").getInputStream();
				prop.load(stream);
				String welcome = prop.getProperty("welcome");
				messages.add(welcome);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		ZoneId zEastern=ZoneId.of("America/New_York");
		ZoneId zUTC=ZoneId.of("UTC");
		ZoneId zMountain=ZoneId.of("America/Denver");
		ZoneId zoneId=ZoneId.systemDefault();

		// set local time for presentation
		LocalDateTime currentDateTime = LocalDateTime.now();

		// create formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		// Convert the current time to Eastern Time
		ZonedDateTime zonedDateTimeEastern = currentDateTime.atZone(zoneId).withZoneSameInstant(zEastern);
		String EST = zonedDateTimeEastern.format(formatter);
		presentationTimes.add(EST);
		System.out.println("Eastern time: " + EST);

		// Convert the current time to Mountain Time
		ZonedDateTime zonedDateTimeMountain = currentDateTime.atZone(zoneId).withZoneSameInstant(zMountain);
		String MST = zonedDateTimeMountain.format(formatter);
		presentationTimes.add(MST);
		System.out.println("Mountain time: " + MST);

		// Convert the current time to UTC
		ZonedDateTime zonedDateTimeUTC = currentDateTime.atZone(zoneId).withZoneSameInstant(zUTC);
		String UTC = zonedDateTimeUTC.format(formatter);
		presentationTimes.add(UTC);
		System.out.println("UTC time: " + UTC);
	}

	public static List<String> getMessages() {
		return messages;
	}

	public static List<String> getPresentationTimes() {
		return presentationTimes;
	}

}
