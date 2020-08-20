package com.simulation.server.chatlog.controller;

import com.simulation.server.chatlog.SharedConstants;
import com.simulation.server.chatlog.model.Chat;
import com.simulation.server.chatlog.model.Chatlog;
import com.simulation.server.chatlog.service.ChatlogService;
import com.simulation.server.chatlog.service.RecordUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatlogs")
public class ChatlogController {

    @Autowired
    private ChatlogService chatlogService;

    @PostMapping("/{user}/")
    public Integer processChat(@PathVariable String user,  @RequestBody String body) {

        return chatlogService.postChat(user, body);

    }

    @GetMapping("/{user}")
    public List<Chat> getChatlogs(@PathVariable String user, @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "start", required = false) Integer start) {

        return chatlogService.getChats(user, limit, start);
    }

    @DeleteMapping("/{user}")
    public void deleteChatlogs(@PathVariable String user) {
        chatlogService.deleteChats(user);
    }

    @DeleteMapping("/{user}/{msgId}")
    public String deleteChatlogs(@PathVariable String user, @PathVariable Integer msgId) {
        if (chatlogService.deleteSpecificChat(user, msgId) == 0) {
            throw new RecordUnavailableException(SharedConstants.BAD_REQUEST_STATUS, SharedConstants.BAD_REQUEST_REASON.concat(user));
        }
        return "";
    }


}
