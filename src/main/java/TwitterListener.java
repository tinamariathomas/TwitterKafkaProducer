import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;


class TweetListener implements StatusListener {

    KafkaProducer producer;

    public TweetListener (){
        Properties props = new Properties();;
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.setProperty(ProducerConfig.METADATA_FETCH_TIMEOUT_CONFIG,Integer.toString(5 * 1000));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer(props);
    }

    public void onStatus(Status status) {
        try {
            if (status.getLang().equals("en") && !status.isRetweet()) {
                String statusText = status.getText();
                System.out.println(statusText);
                ProducerRecord<String,String> data = new ProducerRecord("test",statusText);
                producer.send(data);
            }
        } catch (Exception e) {
            System.out.println("wrong");
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


        TweetListener tweetListener = new TweetListener();

        twitterStream.addListener(tweetListener);

        twitterStream.sample();
    }
}
