package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Message;
import com.yjj.campustradeplatform.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MessageMapper messageMapper;

    @GetMapping("/contacts")
    public List<Map<String, Object>> getContacts(@RequestParam Long userId) {
        return messageMapper.selectContacts(userId);
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam Long userId, @RequestParam Long targetId) {
        return messageMapper.selectByUserIds(userId, targetId);
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        message.setReadStatus(0);
        return messageMapper.insertChatMessage(message) > 0 ? "success" : "发送失败";
    }

    @PostMapping("/read")
    public void markAsRead(@RequestParam Long userId, @RequestParam Long targetId) {
        messageMapper.updateReadStatus(targetId, userId);
    }

    @GetMapping("/unread-count")
    public int getUnreadCount(@RequestParam Long userId) {
        return messageMapper.countUnreadChat(userId);
    }
}
