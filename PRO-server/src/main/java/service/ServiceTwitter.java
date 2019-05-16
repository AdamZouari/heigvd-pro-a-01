package service;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ServiceTwitter extends Service {

    private Twitter twitter;
    private HttpURLConnection connection;
    private ConfigurationBuilder cb;

    public ServiceTwitter() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("n9E1yBbhc1hSBGj61iU3lvGT6")
                .setOAuthConsumerSecret("b36cQpIffHxvsG6BofnvxX9l9x488e0hxEd5sx1bwthmKmddSJ");
    }

    @Override
    public void connect() {
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();

            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            AccessToken accessToken = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");

                try {
                    String pin = br.readLine();
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken();
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }   catch (IOException ex) {
                    System.out.println("Error getting your pin");
                }

            }
            //persist to the accessToken for future reference.
            //storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
            Status status = twitter.updateStatus("Salut");
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
            System.exit(0);
        } catch (TwitterException | IllegalStateException ie) {

            if (!twitter.getAuthorization().isEnabled()) {
                System.out.println("OAuth consumer key/secret is not set");
            }
        }

    }

    @Override
    public void disconnect() {

    }

    public void postTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void getTimeline() {

    }

    public void sendMessages(long id, String message) {
        try {
            twitter.sendDirectMessage(id, message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
