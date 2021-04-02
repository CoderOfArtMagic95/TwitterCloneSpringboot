package com.tts.TechTalentTwitter.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*  hashtags let users know that they're talking
 *  about the same subject as other users
 *  So if your tweet has #springboot within the message,
 *  you should be able to click on that phrase (as a link),
 *  and see every other tweet that contains that hashtagged-phrase
 *  
 *  Hashtags will not only be phrases within Tweets,
 *  they will also be a resource unto themselves*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    private String phrase;

    @ManyToMany(mappedBy = "tags")
    private List<Tweet> tweets;

    // If Lombok doesn't work for you then use:
    // public Long getId() {
    // return id;
    // }

    // public String getPhrase() {
    // return phrase;
    // }

    // public void setPhrase(String phrase) {
    // this.phrase = phrase;
    // }

    // public List<Tweet> getTweets() {
    // return tweets;
    // }

    // public void setTweets(List<Tweet> tweets) {
    // this.tweets = tweets;
    // }

    // @Override
    // public String toString() {
    // return "Tag [id=" + id + ", phrase=" + phrase + ", tweets=" + tweets + "]";
    // }

}
