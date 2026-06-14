package com.yjj.campustradeplatform.controller;

import com.yjj.campustradeplatform.entity.Message;
import com.yjj.campustradeplatform.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;

    @PostMapping("/send")
    public String send(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setType(1);
        message.setReadStatus(0);
        messageMapper.insert(message);
        return "发送成功";
    }

    @PostMapping("/send/system")
    public String sendSystem(@RequestParam Long receiverId, @RequestParam String content, @RequestParam Long relatedId) {
        Message message = new Message();
        message.setSenderId(0L);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setType(2);
        message.setRelatedId(relatedId);
        message.setReadStatus(0);
        messageMapper.insert(message);
        return "发送成功";
    }

    @GetMapping("/list")
    public List<Message> list(@RequestParam Long receiverId) {
        return messageMapper.findByReceiverId(receiverId);
    }

    @PostMapping("/read/{id}")
    public String markAsRead(@PathVariable Long id) {
        messageMapper.markAsRead(id);
        return "已读";
    }

    @PostMapping("/read/all/{receiverId}")
    public String markAllAsRead(@PathVariable Long receiverId) {
        messageMapper.markAllAsRead(receiverId);
        return "全部已读";
    }

    @GetMapping("/unread/count")
    public Map<String, Object> countUnread(@RequestParam Long receiverId) {
        Map<String, Object> result = new HashMap<>();
        result.put("count", messageMapper.countUnread(receiverId));
        return result;
    }
}
