package com.tts.TechTalentTwitter.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.*;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TweetService tweetService;
    
    private void SetTweetCounts(List<User> users, Model model) {
    	/*  Within SetTweetCounts under our new HashMap,
    	 *  we need to iterate through each user, getting a
    	 *  list of their tweets and adding the size of that
    	 *  list to the HashMap.*/
        HashMap<String,Integer> tweetCounts = new HashMap<>();
        for (User user : users) {
            List<Tweet> tweets = tweetService.findAllByUser(user);
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);
    }
    
    //add more code here
    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value="username") String username, Model model) {	
      User user = userService.findByUsername(username);
      List<Tweet> tweets = tweetService.findAllByUser(user);
      /* In the UserController's getUser method, 
       *  add the following. We iterate through the
       *  list of users that are being followed by
       *  the currently logged in user to see if the
       *  user whose profile we are viewing is one of them.*/
       User loggedInUser = userService.getLoggedInUser();
       List<User> following = loggedInUser.getFollowing();
       boolean isFollowing = false;
       for (User followedUser : following) {
           if (followedUser.getUsername().equals(username)) {
               isFollowing = true;
           }
       }
       model.addAttribute("following", isFollowing);
       model.addAttribute("tweetList", tweets);
       model.addAttribute("user", user);
       return "user";
    }
   
    @GetMapping(value = "/users")
    public String getUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        SetTweetCounts(users, model);
        return "users";
    }
}