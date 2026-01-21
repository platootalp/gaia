package github.grit.gaia.agent.infra.ai.agent;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public class PlanAndSolveAgent extends Agent {

    /** genAI_master_start */
    /**
     * PlanAndSolve范式提示词常量
     */
    private static final String PLAN_AND_SOLVE_PARADIGM_PROMPT = "You are using the Plan-and-Solve approach to solve problems.";

    @Override
    protected String getParadigmPrompt() {
        return PLAN_AND_SOLVE_PARADIGM_PROMPT;
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
