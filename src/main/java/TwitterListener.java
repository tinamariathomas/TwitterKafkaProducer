import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;


class TweetListener implements StatusListener {

    LinkedBlockingQueue<String> queue = null;
    KafkaProducer producer;

    public TweetListener (){
        Properties props = new Properties();;
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        props.put("bootstrap.servers", "127.0.0.1:8080");
        props.put("key.serializer", org.apache.kafka.common.serialization.ByteArraySerializer.class);
        props.put("value.serializer", org.apache.kafka.common.serialization.ByteArraySerializer.class);
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


        TweetListener tweetListener = new TweetListener();

        twitterStream.addListener(tweetListener);

        twitterStream.sample();
    }
}
