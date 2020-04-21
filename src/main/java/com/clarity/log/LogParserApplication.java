package com.clarity.log;

import com.clarity.log.application.JobFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogParserApplication implements ApplicationRunner {
	private static final String FOLLOW_FLAG = "follow";
	private static final String MODE_ARG = "mode";
	private static final String INTERVAL_MODE = "interval";
	private static final String UNLIMITED_MODE = "unlimited";

	private final JobFactory jobFactory;

	public LogParserApplication(JobFactory jobFactory) {
		this.jobFactory = jobFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(LogParserApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!args.containsOption(MODE_ARG)) return;

		String mode = args.getOptionValues(MODE_ARG).get(0);

		try {
			switch (mode) {
				case INTERVAL_MODE:
					jobFactory.parseTimeInterval().run();
					break;
				case UNLIMITED_MODE:
					boolean follow = args.containsOption(FOLLOW_FLAG);
					jobFactory.parseUnlimitedInput().run(follow);
					break;
				default:
					throw new IllegalArgumentException("Must provide application mode");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
