package github.grit.gaia.agent.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI 配置
 * 
 * @author Gaia Team
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.ai.qwen")
public class AiConfig {
    
    /** API Key */
    private String apiKey;
    
    /** 模型名称 */
    private String model = "qwen-plus";
    
    /** 温度参数 */
    private Double temperature = 0.7;
    
    /** 最大 Token 数 */
    private Integer maxTokens = 2000;
    
    /** Top P 参数 */
    private Double topP = 0.9;
}
