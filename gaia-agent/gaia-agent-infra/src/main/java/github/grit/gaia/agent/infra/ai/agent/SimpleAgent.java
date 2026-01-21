package github.grit.gaia.agent.infra.ai.agent;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public class SimpleAgent extends Agent{
    /** genAI_master_start */
    /**
     * Simple范式提示词常量
     */
    private static final String SIMPLE_PARADIGM_PROMPT = "You are a helpful assistant.";

    @Override
    protected String getParadigmPrompt() {
        return SIMPLE_PARADIGM_PROMPT;
    }
    /** genAI_master_end */

    @Override
    public ChatResponse call(Prompt prompt) {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        return null;
    }
}
