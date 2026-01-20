package infra.ai;

import infra.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ChatClientTest {

    @Autowired
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;


    @Test
    public void testChatClientCall() {
        ChatClient client = ChatClient.create(chatModel);
        String content = client.prompt()
                .user("中国20岁男性平均身高是多少？")
                .call()
                .content();
        System.out.println(content);
    }


    @Test
    public void testChatClientStream() {
        ChatClient client = ChatClient.create(chatModel);
        client.prompt()
                .user("中国20岁男性各省市身高排名？")
                .stream()
                .content()
                .doOnNext(System.out::print)
                .blockLast();
    }
}
