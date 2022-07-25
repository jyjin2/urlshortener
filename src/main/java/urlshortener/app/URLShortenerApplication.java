package urlshortener.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class URLShortenerApplication {
    public static void main(final String[] args) {
        SpringApplication.run(URLShortenerApplication.class, args);

        URLShortenerApplication testClz = new URLShortenerApplication();
        testClz.test();
    }

    private void test() {}
}
