package com.simulation.server.chatlog.service;

import com.simulation.server.chatlog.SharedConstants;
import com.simulation.server.chatlog.model.Chat;
import com.simulation.server.chatlog.model.Chatlog;
import com.simulation.server.chatlog.model.User;
import com.simulation.server.chatlog.repository.ChatRepository;
import com.simulation.server.chatlog.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatlogService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(ChatlogService.class);

    private Integer insertChat(Integer id, String body) {
        try {

            JSONObject jsonObject = new JSONObject(body);

            Chatlog chatlog = new Chatlog();
            chatlog.setUserID(id);
            chatlog.setMessage(jsonObject.getString("message"));
            chatlog.setTimestamp(jsonObject.getLong("timestamp"));
            chatlog.setIsSent(jsonObject.getInt("isSent"));

            Chatlog returnChatlog = chatRepository.save(chatlog);

            if (returnChatlog != null) {
                return returnChatlog.getMessageID();
            }
        }
        catch (JSONException e1) {
            LOG.error("Error parsing data from the API");
        }

        return -1;
    }

    public Integer postChat(String user, String body) {
        User fetchUser = findUser(user);

        if (fetchUser != null) {
            return insertChat(fetchUser.getUserID(), body);
        }

        LOG.error("Error creating User in the DB");
        return -1;
    }

    private User findUser(String user) {

        try {

            User returnUser = userRepository.findByUserName(user);

            if (returnUser != null) {

                return returnUser;

            }
            else {
                User newUser = new User();
                newUser.setUserName(user);

                newUser = userRepository.save(newUser);

                if (newUser != null) {

                    return newUser;
                }
            }
        }
        catch (Exception e) {
            LOG.error("Error fetching User data from the DB");
        }

        return null;
    }

    private String constructGetQuery(Integer id, Integer limit, Integer start) {
        StringBuilder queryBuilder = new StringBuilder("select message, timestamp, is_sent from chatlog where userID = "+id + " ");

        if (start != null) {
            queryBuilder.append("and messageID >= "+start.intValue()+" ");
        }

        if (limit == null) {
            limit = SharedConstants.LIMIT;
        }

        queryBuilder.append("order by timestamp limit ");

        queryBuilder.append(""+limit);

        return queryBuilder.toString();
    }


    public List<Chat> getChats(String user, Integer limit, Integer start) {

        User fetchUser = findUser(user);

        if (fetchUser != null) {

            String query = constructGetQuery(fetchUser.getUserID(), limit, start);

            try {
                return jdbcTemplate.query(query, new ChatlogsMapper());
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }

        }

        return null;
    }

    public void deleteChats(String user) {

        User fetchUser = findUser(user);

        if (fetchUser != null) {
            try {
                jdbcTemplate.execute("delete from chatlog where userID = "+fetchUser.getUserID());
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }

    public Integer deleteSpecificChat(String user, Integer msgId) {

        User fetchUser = findUser(user);

        if (fetchUser != null) {
            try {

                if (chatRepository.findByMessageIDAndUserID(msgId, fetchUser.getUserID()) == null) return 0;

                jdbcTemplate.execute("delete from chatlog where userID = "+fetchUser.getUserID()+" and messageID = "+msgId);

                return 1;
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

        return 0;
    }

}
