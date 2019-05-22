package service;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class ServiceTwitter extends Service {

    private Twitter twitter;
    private HttpURLConnection connection;
    private ConfigurationBuilder cb;

    private String accessToken = "1130810417767473154-w13f7SFTB4CTVFQk1yLGEiRPzYnPeh";
    private String accessTokenSecret = "RzD5X9DuLqgZikMrmZV0vHd4A6Dm6VphnLzBMq1zLhU2k";
    final private String CONSUMER_KEY = "Lgi1IKBOsazJ9L6uaSVzxsMWb";
    final private String CONSUMER_SECRET = "FVzYIg860Re8WXQbhfw8jfUl5rk0ZxUdfXWYJq8pFF9eympB7T";

    public ServiceTwitter() {

        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    /**
     * Recupérer un token et autoriser l'application a utiliser le compte de l'utilisateur
     */
    @Override
    public void connect() {
        /*
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
        */
        try {
            twitter.getOAuthAccessToken(accessToken);
        } catch (TwitterException e) {
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

    public void followUser(String user) {
        try {
            twitter.createFriendship(user);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
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

    public ArrayList<String> getLast10Tweets(String user) {

        ArrayList<String> tweets = new ArrayList<>();

        // Recuperer la timeline et prendre les 10 derniers tweets.
        try {
            ResponseList<Status> timeline = twitter.getUserTimeline(user);
            for (int i = 9; i >= 0; --i) {
                tweets.add(timeline.get(i).getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tweets;
    }
}
