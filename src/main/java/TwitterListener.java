import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.concurrent.LinkedBlockingQueue;


class TweetListener implements StatusListener {

    LinkedBlockingQueue<String> queue = null;

    public void onStatus(Status status) {
        try {
            if (status.getLang().equals("en") && !status.isRetweet()) {
                System.out.println(status.getText());
            }
        } catch (Exception e) {
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

class Main {
    public static void main(String[] args) {

        TwitterStream twitterStream;
        ConfigurationBuilder config = ConfigurationProvider.getConfig();

        TwitterStreamFactory fact =
                new TwitterStreamFactory(config.build());

        twitterStream = fact.getInstance();

        twitterStream.addListener(new TweetListener());

        twitterStream.sample();
    }
}
