package infra.ai;

import infra.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest(classes = TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ModelTest {

    @Autowired
    @Qualifier("dashScopeChatModel")
    private ChatModel chatModel;

    @Autowired
    @Qualifier("dashScopeImageModel")
    private ImageModel imageModel;

    @Test
    public void testChatModelChat() {
        Prompt prompt = new Prompt("你是哪个模型呢");
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response);
    }

    @Test
    public void testChatModelStream() {
        Prompt prompt = new Prompt("什么是spring-ai?");
        Flux<ChatResponse> stream = chatModel.stream(prompt);
        stream.flatMap(response -> Flux.fromIterable(
                response.getResults()))
                .map(result -> result.getOutput().getText())
                .doOnNext(System.out::print)
                .blockLast();
    }

    @Test
    public void testImageModel() {
        ImageMessage imageMessage = new ImageMessage("生成动漫七龙珠中的人物卡卡罗特的超级赛亚人四个阶段的图片");
        ImageOptions options = ImageOptionsBuilder.builder()
                .height(1024).width(1024)
                .N(4)
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(imageMessage, options);
        ImageResponse call = imageModel.call(imagePrompt);
        call.getResults().forEach(result -> {
            System.out.println(result.getOutput().getUrl());
        });
    }
}
