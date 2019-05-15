package service;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.net.HttpURLConnection;

public class ServiceTwitter extends Service {

    Twitter twitter;
    HttpURLConnection connection;

    @Override
    public void connect() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("n9E1yBbhc1hSBGj61iU3lvGT6 ")
                .setOAuthConsumerSecret("b36cQpIffHxvsG6BofnvxX9l9x488e0hxEd5sx1bwthmKmddSJ")
                .setOAuthAccessToken("194071924-djw0w4pI0zbxkFVZngE7J4Q3HVUZkkYgVRk5M2sA")
                .setOAuthAccessTokenSecret("xtAyR83sMvIGzsNv45S708texWK2NtP6Qqt1vfJTSAeRT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public void disconnect() {

    }

    public String getTweets(String user) {
        return null;
    }

    public String postTweets(String tweet) {
        return null;
    }
}
