package rental;

import rental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            assign.setOrderId(scheduleFixed.getOrderId());
            assign.setUserId("Kim");
            assign.setCheckDate(scheduleFixed.getCheckDate());
            assign.setStatus("schedule assigned!");
            assignRepository.save(assign);
            System.out.println("##### listener Assign 11 : " + scheduleFixed.toJson());
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_Assign(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){
            Assign assign = null;
            Optional<Assign> optional = assignRepository.findById(orderCanceled.getId());
            if(optional.isPresent()) {
                assign = optional.get();
                assign.setId(orderCanceled.getId());
                assign.setStatus("assign canceled");
                assignRepository.save(assign);
                System.out.println("##### listener Assign : " + orderCanceled.toJson());
            }else
                System.out.println("##### listener Assign : null ");
        }
    }

}

