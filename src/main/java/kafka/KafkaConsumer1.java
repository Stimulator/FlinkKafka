package kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaConsumer1 {


    public static void main(String[] args) throws Exception {

        String topicName = "apacfk1";
        String groupName = "myGroup";

        Properties props = new Properties ( );
        props.put ("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put ("group.id", groupName);
        props.put ("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put ("value.deserializer", "kafka.DeserializingData");


        KafkaConsumer <String, MachineData> consumer = new KafkaConsumer <> (props);
        consumer.subscribe (Arrays.asList (topicName));

        try {
            while ( true ) {

                ConsumerRecords <String, MachineData> records = consumer.poll (500);
                for (TopicPartition partition : records.partitions ()) {
                    List<ConsumerRecord<String, MachineData>> partitionRecords = records.records (partition);
                    for (ConsumerRecord <String, MachineData> record : partitionRecords) {
                        System.out.println ("Engine-id= " + String.valueOf (record.value ( ).getEngineId ( )) +
                                " Engine-Name = "
                                + record.value ( ).getEngineName ( )
                                + " Engine-StartDate = " + record.value ( ).getStartDate ( ).toString ( ) +
                                " Engine-Temperature= " + record.value ( ).getvTemp ( )
                                + " Engine-Vibration= " + record.value ( ).getVibration ());
                    }
                    long lastOffset = partitionRecords.get (partitionRecords.size () -1).offset ();
                    consumer.commitSync (Collections.singletonMap (partition, new OffsetAndMetadata (lastOffset +1)));

                }
            }
        } finally {
            consumer.close();
        }
    }
}
