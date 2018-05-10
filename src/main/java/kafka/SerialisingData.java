package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class SerialisingData<M> implements Serializer<MachineData> {


    public void configure(Map configs, boolean isKey) {

    }
    public byte[] serialize(String topic, MachineData data) {

        byte[] retVal = null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            retVal = objectMapper.writeValueAsString(data).getBytes();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return retVal;
    }

    public void close() {

    }
}