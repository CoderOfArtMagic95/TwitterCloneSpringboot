package com.tts.TechTalentTwitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.model.Tag;
import com.tts.TechTalentTwitter.repository.TweetRepository;
import com.tts.TechTalentTwitter.repository.TagRepository;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    //finds all tweets
    public List<Tweet> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return tweets;
        //return formatTweets(tweets);
    }
	//finds all tweets made by specific one user with User class object user
    public List<Tweet> findAllByUser(User user) {
        List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
        return tweets;
    }
	//finds all the tweets made by the specified list of users
    public List<Tweet> findAllByUsers(List<User> users){
        List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
        return tweets;
    }
	//saves the tweet message
    public void save(Tweet tweet) {
    	handleTags(tweet);
        tweetRepository.save(tweet);
    }
    
    public List<Tweet> findAllWithTag(String tag){
        List<Tweet> tweets = tweetRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
        return tweets;
        //return formatTweets(tweets);
    }
    
    private void handleTags(Tweet tweet) {
        List<Tag> tags = new ArrayList<Tag>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(tweet.getMessage());
        while (matcher.find()) {
            String phrase = matcher.group().substring(1).toLowerCase();
            Tag tag = tagRepository.findByPhrase(phrase);
            if (tag == null) {
                tag = new Tag();
                tag.setPhrase(phrase);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }
        tweet.setTags(tags);
    }
    
    private List<Tweet> formatTweets(List<Tweet> tweets) {
        addTagLinks(tweets);
        shortenLinks(tweets);
        return tweets;
    }
    //addTagLinks, shortenLinks takes in a list of tweets
    //and changes the message for each one.
    private void shortenLinks(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("https?[^ ]+");
        // iterate through the list and process each one
        for(Tweet tweet: tweets) {
        	/* extract the message from the tweet object,
        	 * apply the regular expression to find all links,
        	 * and then iterate through the found links one by one*/
        	String message = tweet.getMessage();
        	Matcher matcher = pattern.matcher(message);
        	while(matcher.find()) {
        	    String link = matcher.group();
                
        	    //For each link, we begin by shortening it if necessary
        	    String shortenedLink = link;
        	    if (link.length() > 23) {
        	        shortenedLink = link.substring(0,20) + "...";
        	    }
        	    
        	    message = message.replace(link, "<a class=\"tag\" href=\"" + link + 
        	    		"\target=\"_blank\">" + shortenedLink + "</a>");
            }
            tweet.setMessage(message);
        }
    }
}