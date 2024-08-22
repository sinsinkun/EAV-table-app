package org.access;

import org.database.EavInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    EavInterface eav;

    public Controller() {
        this.eav = new EavInterface("localhost:3306", "localdb");
    }

    @GetMapping("/")
    public String index() {
        return "Hello world";
    }
}
