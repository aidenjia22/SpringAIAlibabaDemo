package com.example.controller;

import com.example.model.ActorFilms;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author aiden.jia
 */
@RestController
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/simple/chat")
    public String simpleChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query) {
        return chatClient.prompt(query).call().content();
    }

    @GetMapping("/simple/chatResponse")
    public ChatResponse chatResponse(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query) {
        return chatClient.prompt(query).call().chatResponse();
    }

    /**
     * ChatClient 流式调用
     */
    @GetMapping("/stream/chat")
    public Flux<String> streamChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt(query).stream().content();
    }

    /**
     * 输入演员名字，返回其演过的电影和简介列表
     */
    @GetMapping("/actor/films")
    public List<ActorFilms> getActorFilms(@RequestParam(value = "actorName", defaultValue = "周星驰") String actorName) {
        List<ActorFilms> actorFilms = chatClient.prompt("请列出演员 " + actorName + " 的5部电影的作品目录。")
                .user("请列出相关演员距离当前时间最近的10部电影的作品目录。")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
        return actorFilms;
    }
}
