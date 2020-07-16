package rental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Assign_table")
public class Assign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String userId;
    private Long orderId;
    private String checkDate;
    private String status;
/*
    @PostPersist
    public void onPostPersist(){
        Assigned assigned = new Assigned();
        BeanUtils.copyProperties(this, assigned);
        assigned.publishAfterCommit();


    }

 */
    @PostPersist
    public void onPostPersist(){
        Assigned assigned = new Assigned();
        BeanUtils.copyProperties(this, assigned);
        assigned.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rental.external.User user = new rental.external.User();
        // mappings goes here
        user.setUserId(getUserId());
        user.setStatus("assigned");
        AssignApplication.applicationContext.getBean(rental.external.UserService.class)
                .manage(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
