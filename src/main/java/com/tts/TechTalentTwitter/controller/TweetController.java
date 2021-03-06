package com.tts.TechTalentTwitter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.TweetDisplay;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;

/* Uses the UserService and TweetService
 * classes from the service package*/

@Controller
public class TweetController {
    @Autowired
    private UserService userService;
	
    @Autowired
    private TweetService tweetService;
    
    /* The first will allow us to get all tweets,
     *  and will accept a GET request to either /tweets or /. */
    @GetMapping(value= {"/tweets", "/"})
    public String getFeed(Model model,boolean IsEmpty){
        List<TweetDisplay> tweets = tweetService.findAll();
        /* allow us to check for 'feed' if it is empty, 
         * newTweet.html */
        if(IsEmpty == true && model.equals(null)) {
        	model.addAttribute("emptyMessage", "Tweet feed is empty!");
        }
        model.addAttribute("tweetList", tweets);
        getFeed(model, IsEmpty);
        
        return "feed";
    }
    /* allow us to serve up the 'new tweet' page, 
     * newTweet.html */
    @GetMapping(value = "/tweets/new")
    public String getTweetForm(Model model) {
        model.addAttribute("tweet", new Tweet());
        return "newTweet";
    }
    
   
    /*This method handles the form submission from the 'new tweet' page. 
     * This method gets the logged in user and associates them with the tweet */
    @PostMapping(value = "/tweets")
    public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
        User user = userService.getLoggedInUser();
        if (!bindingResult.hasErrors()) {
            tweet.setUser(user);
            tweetService.save(tweet);
            model.addAttribute("successMessage", "Tweet successfully created!");
            model.addAttribute("tweet", new Tweet());
        }
        return "newTweet";
    }
   
    /*a method that is called whenever we make a GET request to /tweets/{tag}*/
    @GetMapping(value = "/tweets/{tag}")
    public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
        List<TweetDisplay> tweets = tweetService.findAllWithTag(tag);
        model.addAttribute("tweetList", tweets);
        model.addAttribute("tag", tag);
        return "taggedTweets";
    }
}