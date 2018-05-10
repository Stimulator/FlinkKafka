package kafka;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.KafkaSink;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;
import org.apache.flink.streaming.util.serialization.SerializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Simple example for writing data into Kafka.
 *
 * The following arguments are required:
 *
 *  - "bootstrap.servers" (comma separated list of kafka brokers)
 *  - "topic" the name of the topic to write data to.
 *
 * This is an example command line argument:
 *  "--topic test --bootstrap.servers localhost:9092"
 */
public class WriteIntoKafka {
    public static void main(String[] args) throws Exception {
        // create execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment ();

        // parse user parameters
        ParameterTool parameterTool = ParameterTool.fromArgs(args);

        // add a simple source which is writing some strings
        DataStream<String> messageStream = env.addSource(new SimpleStringGenerator());

        // write stream to Kafka
        messageStream.addSink(new KafkaSink<>(parameterTool.getRequired("bootstrap.servers"),
                parameterTool.getRequired("topic"),
                new SimpleStringSchema()));

        env.execute();
    }

    public static class SimpleStringGenerator implements SourceFunction<String> {
        private static final long serialVersionUID = 2174904787118597072L;
        boolean running = true;
        long i = 0;
        @Override
        public void run(SourceContext<String> ctx) throws Exception {

                DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
                MachineData m1 = new MachineData (101,"Eng1",df.parse("2016-04-01"),65,100);
                MachineData m2 = new MachineData (102,"Eng2",df.parse("2012-01-01"),70,200);

                System.out.println ("Lets start Writing!");

                ctx.collect ("Readings: " + m1.getEngineName () + m1.getEngineId () + m1.getStartDate () + m1.getvTemp () + m1.getVibration ());
                ctx.collect ("Readings: " + m2.getEngineName () + m2.getEngineId () + m2.getStartDate () + m2.getvTemp () + m2.getVibration ());
        }

        @Override
        public void cancel() {
            running = false;
        }
    }


    public static class SimpleStringSchema implements DeserializationSchema<String>, SerializationSchema<String, byte[]> {
        private static final long serialVersionUID = 1L;

        public SimpleStringSchema() {
        }

        public String deserialize(byte[] message) {
            return new String(message);
        }

        public boolean isEndOfStream(String nextElement) {
            return false;
        }

        public byte[] serialize(String element) {
            return element.getBytes();
        }

        public TypeInformation<String> getProducedType() {
            return TypeExtractor.getForClass(String.class);
        }
    }
}