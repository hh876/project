package com.yjj.campustradeplatform.mapper;

import com.yjj.campustradeplatform.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO chat_message(sender_id, receiver_id, content, read_status, create_time) " +
            "VALUES(#{senderId}, #{receiverId}, #{content}, #{readStatus}, NOW())")
    int insertChatMessage(Message message);

    @Select("SELECT * FROM chat_message WHERE (sender_id=#{userId} AND receiver_id=#{targetId}) OR " +
            "(sender_id=#{targetId} AND receiver_id=#{userId}) ORDER BY create_time ASC")
    List<Message> selectByUserIds(@Param("userId") Long userId, @Param("targetId") Long targetId);

    @Update("UPDATE chat_message SET read_status=1 WHERE receiver_id=#{receiverId} AND sender_id=#{senderId}")
    int updateReadStatus(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Select("SELECT u.id, u.username, u.nickname, MAX(m.create_time) as lastTime, " +
            "SUBSTRING_INDEX(GROUP_CONCAT(m.content ORDER BY m.create_time DESC), ',', 1) as lastMessage, " +
            "SUM(CASE WHEN m.read_status=0 AND m.receiver_id=#{userId} THEN 1 ELSE 0 END) as unreadCount " +
            "FROM user u LEFT JOIN chat_message m ON (u.id=m.sender_id AND m.receiver_id=#{userId}) OR " +
            "(u.id=m.receiver_id AND m.sender_id=#{userId}) " +
            "WHERE u.id != #{userId} AND m.id IS NOT NULL " +
            "GROUP BY u.id ORDER BY lastTime DESC")
    List<java.util.Map<String, Object>> selectContacts(Long userId);

    @Select("SELECT COUNT(*) FROM chat_message WHERE receiver_id=#{userId} AND read_status=0")
    int countUnreadChat(Long userId);

    // 系统消息相关方法
    @Insert("INSERT INTO message(sender_id, receiver_id, content, type, related_id, read_status, create_time) " +
            "VALUES(#{senderId}, #{receiverId}, #{content}, #{type}, #{relatedId}, #{readStatus}, NOW())")
    int insert(Message message);

    @Select("SELECT * FROM message WHERE receiver_id=#{receiverId} ORDER BY create_time DESC")
    List<Message> findByReceiverId(Long receiverId);

    @Update("UPDATE message SET read_status=1 WHERE id=#{id}")
    int markAsRead(Long id);

    @Update("UPDATE message SET read_status=1 WHERE receiver_id=#{receiverId}")
    int markAllAsRead(Long receiverId);

    @Select("SELECT COUNT(*) FROM message WHERE receiver_id=#{userId} AND read_status=0")
    int countUnread(Long userId);
}
