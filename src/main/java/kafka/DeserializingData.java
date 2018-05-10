package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DeserializingData implements Deserializer<MachineData> {

    @Override
    public MachineData deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();

        MachineData machdata = null;

        try {

            machdata = mapper.readValue(data, MachineData.class);

        } catch (Exception e) {

            e.printStackTrace();

        } return machdata;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map configs, boolean arg1) {

    }
}
