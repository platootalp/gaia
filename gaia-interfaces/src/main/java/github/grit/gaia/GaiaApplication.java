package github.grit.gaia;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("github.grit.gaia.infrastructure.persistence.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GaiaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GaiaApplication.class, args);
    }
} 