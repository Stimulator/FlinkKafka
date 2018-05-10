package kafka;

import org.apache.kafka.clients.producer.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class KafkaProducer1 {


    public static void main(String[] args) throws Exception{


        String topicName = "apacfk1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "kafka.SerialisingData");

        Producer<String, MachineData> producer = new KafkaProducer <>(props);

        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
        MachineData m1 = new MachineData (101,"Eng1",df.parse("2016-04-01"),65,100);
        MachineData m2 = new MachineData (102,"Eng2",df.parse("2012-01-01"),70,200);

        producer.send(new ProducerRecord<String,MachineData>(topicName,"Reading1: ",m1)).get();
        producer.send(new ProducerRecord<String,MachineData>(topicName,"Reading2: ",m2)).get();

        System.out.println("Producer Completed!");
        producer.close();

    }
}
