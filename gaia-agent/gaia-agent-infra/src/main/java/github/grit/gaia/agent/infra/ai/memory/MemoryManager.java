package github.grit.gaia.agent.infra.ai.memory;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ai.chat.memory.ChatMemory;

@Data
@AllArgsConstructor
public class MemoryManager {
    protected ChatMemory memory;


}
