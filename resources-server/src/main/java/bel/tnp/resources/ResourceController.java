package bel.tnp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dmitry on 2/25/2017.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping("/")
    public Message home() {
        return new Message("Hello World");
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
