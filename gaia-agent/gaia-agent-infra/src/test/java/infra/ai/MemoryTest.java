package infra.ai;

import infra.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MemoryTest {
    @Autowired
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;

    @Test
    public void testChatMemory() {
        ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(5).build();
        String conversationId = "001";
        // First interaction
        UserMessage userMessage1 = new UserMessage("My name is James Bond");
        chatMemory.add(conversationId, userMessage1);
        ChatResponse response1 = chatModel.call(new Prompt(chatMemory.get(conversationId)));
        chatMemory.add(conversationId, response1.getResult().getOutput());

        // Second interaction
        UserMessage userMessage2 = new UserMessage("What is my name?");
        chatMemory.add(conversationId, userMessage2);
        ChatResponse response2 = chatModel.call(new Prompt(chatMemory.get(conversationId)));
        chatMemory.add(conversationId, response2.getResult().getOutput());

        List<Message> messages = chatMemory.get(conversationId);
        messages.forEach(message -> System.out.println(
                "----------------------\n" +
                        message.getText() + "\n----------------------"));
    }
}
