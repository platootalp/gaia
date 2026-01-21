package github.grit.gaia.agent.infra.ai.agent;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public class SimpleAgent extends Agent {
    private static final String SIMPLE_PARADIGM_PROMPT = "You are a helpful assistant.";

    @Override
    protected String getParadigmPrompt() {
        return SIMPLE_PARADIGM_PROMPT;
    }

    @Override
    public ChatResponse call(Prompt prompt) {

        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        return null;
    }
}
