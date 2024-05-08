package city.qianniu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final OllamaChatClient chatClient;
    private final PromptManagementService promptManagementService;

    public ChatService(OllamaChatClient chatClient, PromptManagementService promptManagementService) {
        this.chatClient = chatClient;
        this.promptManagementService = promptManagementService;
    }

    public String establishChat() {
        String chatId = UUID.randomUUID().toString();
        logger.debug("Establishing chat with chatId: {}", chatId);
        promptManagementService.establishChat(chatId);
        return chatId;
    }

    public Flux<ChatResponse> chat(String chatId, String message) {
        Message systemMessage = promptManagementService.getSystemMessage(chatId, message);
        UserMessage userMessage = new UserMessage(message);
        promptManagementService.addMessage(chatId, userMessage);
        logger.debug("Chatting with chatId: {} and message: {}", chatId, message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient.stream(prompt);
    }
}
