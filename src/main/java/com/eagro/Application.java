package com.eagro;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.eagro.config.DefaultProfileUtil;

@SpringBootApplication
public class Application {
	 private static final Logger log = LoggerFactory.getLogger(Application.class);

	    private final Environment env;

	    public Application(Environment env) {
	        this.env = env;
	    }

	    /**
	     * Initializes final.
	     * <p>
	     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
	     * <p>
	     *
	     */
	    @PostConstruct
	    public void initApplication() {
	        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
	        if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
	            log.error("You have misconfigured your application! It should not run " +
	                "with both the 'dev' and 'prod' profiles at the same time.");
	        }
	    }

	    /**
	     * Main method, used to run the application.
	     *
	     * @param args the command line arguments
	     * @throws UnknownHostException if the local host name could not be resolved into an address
	     */
	    public static void main(String[] args) throws UnknownHostException {
	        SpringApplication app = new SpringApplication(Application.class);
	        DefaultProfileUtil.addDefaultProfile(app);
	        Environment env = app.run(args).getEnvironment();
	        String protocol = "http";
	        if (env.getProperty("server.ssl.key-store") != null) {
	            protocol = "https";
	        }
	        log.info("\n----------------------------------------------------------\n\t" +
	                "Application '{}' is running! Access URLs:\n\t" +
	                "Local: \t\t{}://localhost:{}\n\t" +
	                "External: \t{}://{}:{}\n\t" +
	                "Profile(s): \t{}\n----------------------------------------------------------",
	            env.getProperty("spring.application.name"),
	            protocol,
	            env.getProperty("server.port"),
	            protocol,
	            InetAddress.getLocalHost().getHostAddress(),
	            env.getProperty("server.port"),
	            env.getActiveProfiles());
	    }
}
