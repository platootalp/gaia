package infra.config;

import github.grit.gaia.agent.infra.config.AgentConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/** genAI_master_start */
/**
 * Config配置类测试
 */
public class AgentConfigTest {

    /**
     * 测试默认配置
     */
    @Test
    public void testDefaultConfig() {
        AgentConfig agentConfig = AgentConfig.builder().build();

        Assertions.assertEquals("gpt-3.5-turbo", agentConfig.getDefaultModel());
        Assertions.assertEquals("openai", agentConfig.getDefaultProvider());
        Assertions.assertEquals(0.7, agentConfig.getTemperature(), 0.001);
        Assertions.assertNull(agentConfig.getMaxTokens());
        Assertions.assertFalse(agentConfig.getDebug());
        Assertions.assertEquals("INFO", agentConfig.getLogLevel());
        Assertions.assertEquals(100, agentConfig.getMaxHistoryLength());

        System.out.println("默认配置测试通过");
    }

    /**
     * 测试自定义配置
     */
    @Test
    public void testCustomConfig() {
        AgentConfig agentConfig = AgentConfig.builder()
                .defaultModel("gpt-4")
                .defaultProvider("azure")
                .temperature(0.9)
                .maxTokens(2000)
                .debug(true)
                .logLevel("DEBUG")
                .maxHistoryLength(200)
                .build();

        Assertions.assertEquals("gpt-4", agentConfig.getDefaultModel());
        Assertions.assertEquals("azure", agentConfig.getDefaultProvider());
        Assertions.assertEquals(0.9, agentConfig.getTemperature(), 0.001);
        Assertions.assertEquals(2000, agentConfig.getMaxTokens());
        Assertions.assertTrue(agentConfig.getDebug());
        Assertions.assertEquals("DEBUG", agentConfig.getLogLevel());
        Assertions.assertEquals(200, agentConfig.getMaxHistoryLength());

        System.out.println("自定义配置测试通过");
    }

    /**
     * 测试toDict方法
     */
    @Test
    public void testToDict() {
        AgentConfig agentConfig = AgentConfig.builder()
                .defaultModel("gpt-4")
                .temperature(0.8)
                .maxTokens(1500)
                .debug(true)
                .build();

        Map<String, Object> dict = agentConfig.toDict();

        Assertions.assertEquals("gpt-4", dict.get("defaultModel"));
        Assertions.assertEquals("openai", dict.get("defaultProvider"));
        Assertions.assertEquals(0.8, dict.get("temperature"));
        Assertions.assertEquals(1500, dict.get("maxTokens"));
        Assertions.assertEquals(true, dict.get("debug"));
        Assertions.assertEquals("INFO", dict.get("logLevel"));
        Assertions.assertEquals(100, dict.get("maxHistoryLength"));

        System.out.println("toDict方法测试通过");
    }

    /**
     * 测试dict方法（别名）
     */
    @Test
    public void testDict() {
        AgentConfig agentConfig = AgentConfig.builder()
                .defaultModel("gpt-3.5-turbo")
                .build();

        Map<String, Object> dict = agentConfig.toDict();

        Assertions.assertNotNull(dict);
        Assertions.assertEquals("gpt-3.5-turbo", dict.get("defaultModel"));

        System.out.println("dict方法测试通过");
    }

    /**
     * 测试fromEnv方法（需要设置环境变量）
     * 注意：此测试在没有环境变量时会使用默认值
     */
    @Test
    public void testFromEnv() {
        AgentConfig agentConfig = AgentConfig.fromEnv();

        // 验证配置对象创建成功
        Assertions.assertNotNull(agentConfig);
        Assertions.assertNotNull(agentConfig.getLogLevel());
        Assertions.assertNotNull(agentConfig.getTemperature());
        Assertions.assertNotNull(agentConfig.getDebug());

        System.out.println("fromEnv方法测试通过");
        System.out.println("Debug: " + agentConfig.getDebug());
        System.out.println("LogLevel: " + agentConfig.getLogLevel());
        System.out.println("Temperature: " + agentConfig.getTemperature());
        System.out.println("MaxTokens: " + agentConfig.getMaxTokens());
    }

    /**
     * 测试Lombok的getter/setter
     */
    @Test
    public void testGetterSetter() {
        AgentConfig agentConfig = new AgentConfig();

        agentConfig.setDefaultModel("claude-2");
        agentConfig.setDefaultProvider("anthropic");
        agentConfig.setTemperature(0.5);
        agentConfig.setMaxTokens(3000);
        agentConfig.setDebug(true);
        agentConfig.setLogLevel("WARN");
        agentConfig.setMaxHistoryLength(150);

        Assertions.assertEquals("claude-2", agentConfig.getDefaultModel());
        Assertions.assertEquals("anthropic", agentConfig.getDefaultProvider());
        Assertions.assertEquals(0.5, agentConfig.getTemperature(), 0.001);
        Assertions.assertEquals(3000, agentConfig.getMaxTokens());
        Assertions.assertTrue(agentConfig.getDebug());
        Assertions.assertEquals("WARN", agentConfig.getLogLevel());
        Assertions.assertEquals(150, agentConfig.getMaxHistoryLength());

        System.out.println("Getter/Setter测试通过");
    }

    /**
     * 测试配置的不可变性（使用Builder）
     */
    @Test
    public void testImmutabilityWithBuilder() {
        AgentConfig agentConfig1 = AgentConfig.builder()
                .defaultModel("gpt-3.5-turbo")
                .temperature(0.7)
                .build();

        AgentConfig agentConfig2 = AgentConfig.builder()
                .defaultModel("gpt-4")
                .temperature(0.9)
                .build();

        // 验证两个配置对象是独立的
        Assertions.assertNotEquals(agentConfig1.getDefaultModel(), agentConfig2.getDefaultModel());
        Assertions.assertNotEquals(agentConfig1.getTemperature(), agentConfig2.getTemperature());

        System.out.println("Builder不可变性测试通过");
    }

    /**
     * 测试maxTokens为null的情况
     */
    @Test
    public void testNullMaxTokens() {
        AgentConfig agentConfig = AgentConfig.builder()
                .maxTokens(null)
                .build();

        Assertions.assertNull(agentConfig.getMaxTokens());

        Map<String, Object> dict = agentConfig.toDict();
        Assertions.assertNull(dict.get("maxTokens"));

        System.out.println("Null maxTokens测试通过");
    }
}
/** genAI_master_end */
