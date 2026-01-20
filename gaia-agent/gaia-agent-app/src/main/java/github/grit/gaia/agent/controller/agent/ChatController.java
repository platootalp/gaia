package github.grit.gaia.agent.controller.agent;

import github.grit.gaia.agent.common.response.Result;
import github.grit.gaia.agent.controller.dto.ChatRequest;
import github.grit.gaia.agent.controller.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天控制器
 *
 * @author Gaia Team
 */
@Slf4j
@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    /**
     * 发送聊天消息
     */
    @PostMapping("/send")
    public Result<ChatResponse> sendMessage(@Validated @RequestBody ChatRequest request) {
        log.info("收到聊天消息: {}", request.getMessage());

        // TODO: 实现聊天逻辑
        ChatResponse response = ChatResponse.builder()
                .sessionId(request.getSessionId())
                .message("这是一个示例回复")
                .timestamp(System.currentTimeMillis())
                .build();

        return Result.success(response);
    }

    /**
     * 获取聊天历史
     */
    @GetMapping("/history/{sessionId}")
    public Result<?> getChatHistory(@PathVariable String sessionId) {
        // TODO: 实现获取聊天历史
        return Result.success();
    }
}
