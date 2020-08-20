package com.simulation.server.chatlog.service;

import com.simulation.server.chatlog.model.Chat;
import com.simulation.server.chatlog.model.Chatlog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatlogsMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Chat chat = new Chat();
        chat.setMessage(rs.getString(1));
        chat.setTimestamp(rs.getLong(2));
        chat.setIsSent(rs.getInt(3));

        return chat;
    }
}
