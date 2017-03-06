package bel.tnp.ui;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sefire on 2/28/2017.
 */

@RestController
public class UIController {

    static final String clientId = "1189110511209604";
    static final String clientSecret = "5d3bef32631e0d5c926f46d209cad34b";


    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index.html");

        return modelAndView;
    }

/*    @RequestMapping("/login-fb")
    public ModelAndView loginFb() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:https://www.facebook.com/v2.8/dialog/oauth?client_id=1189110511209604&redirect_uri=http://localhost:8080/samplestring");

        return modelAndView;
    }*/

    @RequestMapping("/resource")
    public Map<String,Object> home() {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World UI");
        return model;
    }

    @RequestMapping("/i")
    public String sampleI() {
        return "IT IS 'i'";
    }

    @RequestMapping("/samplestring")
    public String sampleString() {
        return "Return sample string";
    }

    @RequestMapping("/samplestringAdmin")
    public String sampleStringAdmin() {
        return "Return sample string Admin";
    }


}


