package com.tts.TechTalentTwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
   /* This is what interacts with the database and allows us to convert between
    *  tweet objects in our code and rows in the tweet table*/
	List<Tweet> findAllByOrderByCreatedAtDesc();
    
	List<Tweet> findAllByUserOrderByCreatedAtDesc(User user);
    
	List<Tweet> findAllByUserInOrderByCreatedAtDesc(List<User> users);
    
	List<Tweet> findByTags_PhraseOrderByCreatedAtDesc(String phrase);
}