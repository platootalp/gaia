package github.grit.gaia.agent.infra.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfig {

    /**
     * LLM配置 - 默认模型
     */
    @Builder.Default
    private String defaultModel = "qwen3-max-preview";

    /**
     * LLM配置 - 默认提供商
     */
    @Builder.Default
    private String defaultProvider = "dashscope";

    /**
     * LLM配置 - 温度参数
     */
    @Builder.Default
    private Double temperature = 0.7;

    /**
     * LLM配置 - 最大token数
     */
    private Integer maxTokens;

    /**
     * 系统配置 - 调试模式
     */
    @Builder.Default
    private Boolean debug = false;

    /**
     * 系统配置 - 日志级别
     */
    @Builder.Default
    private String logLevel = "INFO";

    /**
     * 其他配置 - 最大历史长度
     */
    @Builder.Default
    private Integer maxHistoryLength = 100;

    /**
     * 从环境变量创建配置
     *
     * @return Config实例
     */
    public static AgentConfig fromEnv() {
        String debugStr = System.getenv("DEBUG");
        boolean debug = debugStr != null && debugStr.equalsIgnoreCase("true");

        String logLevel = System.getenv("LOG_LEVEL");
        if (logLevel == null) {
            logLevel = "INFO";
        }

        String temperatureStr = System.getenv("TEMPERATURE");
        double temperature = 0.7;
        if (temperatureStr != null) {
            try {
                temperature = Double.parseDouble(temperatureStr);
            } catch (NumberFormatException e) {
                // 使用默认值
            }
        }

        String maxTokensStr = System.getenv("MAX_TOKENS");
        Integer maxTokens = null;
        if (maxTokensStr != null) {
            try {
                maxTokens = Integer.parseInt(maxTokensStr);
            } catch (NumberFormatException e) {
                // 保持为null
            }
        }

        return AgentConfig.builder()
                .debug(debug)
                .logLevel(logLevel)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
    }

    /**
     * 转换为字典（Map）
     *
     * @return 包含所有配置的Map
     */
    public Map<String, Object> toDict() {
        return Map.of(
                "default_model", defaultModel,
                "default_provider", defaultProvider,
                "temperature", temperature,
                "max_tokens", maxTokens,
                "debug", debug,
                "log_level", logLevel,
                "max_history_length", maxHistoryLength
        );
    }
}
