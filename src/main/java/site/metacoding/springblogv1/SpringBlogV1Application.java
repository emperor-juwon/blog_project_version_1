package site.metacoding.springblogv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class SpringBlogV1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogV1Application.class, args);
	}

}
