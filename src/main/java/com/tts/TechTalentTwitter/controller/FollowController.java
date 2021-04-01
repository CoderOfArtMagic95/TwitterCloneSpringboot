package com.tts.TechTalentTwitter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class FollowController {
    @Autowired
    private UserService userService;
    /* the follow method, which will be 
     * called whenever we make a post 
     * request to /follow/{username}*/
    @PostMapping(value = "/follow/{username}")
    public String follow(@PathVariable(value="username") String username, 
                         HttpServletRequest request) {
    	/* Next, we call the UserService to
         * get the currently logged in user
         * as well as the user we want to follow*/
        User loggedInUser = userService.getLoggedInUser();
        User userToFollow = userService.findByUsername(username);
        
        /*  get all of the userToFollow's current followers,
         *  add the currently logged in user to the list, 
         *  and then reassign the updated list to userToFollow.*/
        //checks to make sure we don't follow the same user twice
        List<User> followers = userToFollow.getFollowers();
        followers.add(loggedInUser);
        userToFollow.setFollowers(followers);
        /* Finally, we save userToFollow to flush our changes
         *  to the database and redirect to the last page.*/
        userService.save(userToFollow);
        return "redirect:" + request.getHeader("Referer");
    }
    /*Un-follow method*/
    @PostMapping(value = "/unfollow/{username}")
    public String unfollow(@PathVariable(value="username") String username, HttpServletRequest request) {
        User loggedInUser = userService.getLoggedInUser();
        User userToUnfollow = userService.findByUsername(username);
        List<User> followers = userToUnfollow.getFollowers();
        followers.remove(loggedInUser);
        userToUnfollow.setFollowers(followers);
        userService.save(userToUnfollow);
        return "redirect:" + request.getHeader("Referer");
    }    
}