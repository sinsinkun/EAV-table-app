package org.access;

import org.database.EavInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
public class Controller {

    EavInterface eav;

    // constructor
    public Controller() {
//        this.eav = new EavInterface("localhost:3306", "localdb");
    }

    @RequestMapping(method=RequestMethod.GET, path="/")
    public String index() {
        return "Hello world from SpringBoot";
    }
}
