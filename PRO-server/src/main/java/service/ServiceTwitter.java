package service;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class ServiceTwitter extends Service {

    private Twitter twitter;
    private HttpURLConnection connection;
    private ConfigurationBuilder cb;
    private AccessToken accessToken;

    final private String CONSUMER_KEY = "n9E1yBbhc1hSBGj61iU3lvGT6";
    final private String CONSUMER_SECRET = "b36cQpIffHxvsG6BofnvxX9l9x488e0hxEd5sx1bwthmKmddSJ";

    public ServiceTwitter() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET);
    }

    /**
     * Recupérer un token et autoriser l'application a utiliser le compte de l'utilisateur
     */
    @Override
    public void connect() {
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();

            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            accessToken = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // Recuperer un accessToken si on en a pas
            while (accessToken == null) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
                }
                System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");

                // IOException : a enlever car on passe sur l'UI
                String pin = br.readLine();

                try {
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
                }
            }
            // Stocker l'AT Quelque part pour eviter de se reconnecter a chaque fois
            // storeAccessToken(twitter.verifyCredentials().getId(), accessToken);

        } catch (TwitterException | IllegalStateException | URISyntaxException ie) {

            if (!twitter.getAuthorization().isEnabled()) {
                System.out.println("OAuth consumer key/secret is not set");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {
        accessToken = null;
    }

    public void postTweet(String tweet) {
        try {
            twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages(long id, String message) {
        try {
            twitter.sendDirectMessage(id, message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public boolean isconnected() {
        return accessToken != null;
    }

    public ResponseList<Status> getUserTimeline(String user) throws TwitterException {
        return twitter.getUserTimeline(user);
    }

    public String getLastTweet(String user) {
        String tweet = "";
        try {
            ResponseList<Status> timeline = twitter.getUserTimeline(user);
            tweet = timeline.get(0).getText();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return tweet;
    }
}
