package com.simulation.server.chatlog.repository;

import com.simulation.server.chatlog.model.Chatlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chatlog, Integer> {

    Chatlog findByMessageIDAndUserID(Integer messageID, Integer userID);



}
