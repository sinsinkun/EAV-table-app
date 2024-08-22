package org.access;

import org.database.EavInterface;
import org.database.EavView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class Controller {

    EavInterface eav;

    // constructor
    public Controller() {
        reconnectEav();
    }

    private void reconnectEav() {
        try {
            eav = new EavInterface("localhost:3306", "localdb");
        } catch(Exception e) {
            System.out.println("Err: Could not connect to DB -- " + e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.GET, path="/")
    public String index() {
        if (eav == null) reconnectEav();
        return "Hello world from SpringBoot";
    }

    @RequestMapping(method=RequestMethod.GET, path="/all")
    public List<EavView> getAll() {
        if (eav == null) {
            reconnectEav();
            return null;
        }
        return eav.getEverything();
    }
}
