package project.price_it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
        "project.price_it",
        "project.price_it.aop",
        "project.price_it.service",
        "project.price_it.loader"
})
public class CrowdSourcingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrowdSourcingApiApplication.class, args);
    }
}