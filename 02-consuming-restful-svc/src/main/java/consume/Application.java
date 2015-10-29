package consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final String REMOTE = "http://gturnquist-quoters.cfapps.io/api/random";
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    /*
     * RestTemplate will use Jackson (via a message converter) to convert
     * incoming JSON data into a Quote object.
     */
    @Override
    public void run(String... strings) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject(REMOTE, Quote.class);
        log.info(quote.toString());
    }
}
