import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.concurrent.LinkedBlockingQueue;


class TweetListener implements StatusListener {

    LinkedBlockingQueue<String> queue = null;

    public void onStatus(Status status) {
        // add the tweet into the queue buffer
        try{
        if (status.getLang().equals("en") && !status.isRetweet()) {
            System.out.println(status.getText());
        }}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    public void onTrackLimitationNotice(int i) {
    }

    public void onScrubGeo(long l, long l1) {
    }

    public void onStallWarning(StallWarning warning) {
    }

    public void onException(Exception e) {
        e.printStackTrace();
    }
}
class Main{
    public static void main(String[] args) {
        String custkey, custsecret;
        String accesstoken, accesssecret;
        custkey = "";
        custsecret="";
        accesstoken = "";
        accesssecret = "";
        TwitterStream twitterStream;
        ConfigurationBuilder config =
                new ConfigurationBuilder()
                        .setOAuthConsumerKey(custkey)
                        .setOAuthConsumerSecret(custsecret)
                        .setOAuthAccessToken(accesstoken)
                        .setOAuthAccessTokenSecret(accesssecret)

                ;

        // create the twitter stream factory with the config
        TwitterStreamFactory fact =
                new TwitterStreamFactory(config.build());

        // get an instance of twitter stream
        twitterStream = fact.getInstance();
//        twitterStream.filter();

        // provide the handler for twitter stream
        twitterStream.addListener(new TweetListener());

        // start the sampling of tweets
        twitterStream.sample();
    }
}
