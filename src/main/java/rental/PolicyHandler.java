package rental;

import rental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    AssignRepository assignRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverScheduleFixed_Assign(@Payload ScheduleFixed scheduleFixed){

        if(scheduleFixed.isMe()){
            Assign assign = new Assign();
            assign.setOrderId(scheduleFixed.getId());
            assign.setUserId("A0001");
            assign.setCheckDate(scheduleFixed.getCheckDate());
            assign.setStatus(assign.getUserId() + " : schedule assigned!");
            assignRepository.save(assign);
            System.out.println("##### listener Assign : " + scheduleFixed.toJson());
        }
    }
}

