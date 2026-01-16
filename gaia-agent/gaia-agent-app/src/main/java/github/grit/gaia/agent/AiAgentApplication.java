package github.grit.gaia.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI Agent 应用主类
 * 基于 Spring Boot + spring-ai-alibaba
 * 
 * @author Gaia Team
 */
@SpringBootApplication
public class AiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAgentApplication.class, args);
    }
}
