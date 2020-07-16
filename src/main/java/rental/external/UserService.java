
package rental.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="User", url="http://admin06-sk-user:8080")
public interface UserService {

    @RequestMapping(method= RequestMethod.POST, path="/users")
    public void manage(@RequestBody User user);

}