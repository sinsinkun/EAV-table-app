package org.access;

import org.database.*;
import org.springframework.web.bind.annotation.*;
import org.util.AnsiColors;
import org.util.Fn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@CrossOrigin
public class Controller {

    EavInterface eav;

    // constructor
    public Controller() {
        reconnectEav();
    }

    private void reconnectEav() {
        try {
            eav = new EavInterface("localhost:3306", "localdb");
            Fn.printColor(AnsiColors.GREEN, "Connected to DB");
        } catch(Exception e) {
            Fn.printColor(AnsiColors.RED, "Err: Could not connect to DB -- " + e.getMessage());
        }
    }

    @RequestMapping(method=RequestMethod.GET, path="/")
    public String index() {
        if (eav == null) reconnectEav();
        return "Hello world from SpringBoot";
    }

    @RequestMapping(method=RequestMethod.GET, path="/view/all")
    public List<EavView> getAll() {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        return eav.getEverything();
    }

    @RequestMapping(method=RequestMethod.GET, path="/view/entities")
    public List<EavView> getViewEntities() {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        List<EavEntityType> entityTypes = eav.getEntityTypes();
        List<EavEntity> entities = eav.getEntities();

        // build views
        List<EavView> views = new ArrayList<>();
        for (EavEntity entity : entities) {
            List<EavEntityType> etList = entityTypes.stream()
                    .filter(x -> x.getId() == entity.getEntityTypeId())
                    .collect(Collectors.toList());
            EavView v = new EavView();
            v.setEntityTypeId(Integer.valueOf(entity.getEntityTypeId()));
            v.setEntityId(Integer.valueOf(entity.getId()));
            v.setEntity(entity.getEntity());
            v.setCreatedAt(entity.getCreatedAt());
            if (!etList.isEmpty()) v.setEntityType(etList.get(0).getEntityType());
            views.add(v);
        }

        return views;
    }

    @RequestMapping(method=RequestMethod.GET, path="/entity-types")
    public List<EavEntityType> getAllEntityTypes() {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        return eav.getEntityTypes();
    }

    @RequestMapping(method=RequestMethod.GET, path="/entities")
    public List<EavEntity> getAllEntities() {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        return eav.getEntities();
    }

    @RequestMapping(method=RequestMethod.GET, path="/entities/{type_id}")
    public List<EavEntity> getEntitiesForType(@PathVariable("type_id") Integer typeId) {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        EavEntityType et = eav.getEntityTypeById(typeId);
        return eav.getEntities(et);
    }

    @RequestMapping(method=RequestMethod.GET, path="/attributes")
    public List<EavAttribute> getAllAttrs() {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        return eav.getAttributes();
    }

    @RequestMapping(method=RequestMethod.GET, path="/attributes/{entity_id}")
    public List<EavAttribute> getAttrsForEntity(@PathVariable("entity_id") Integer entityId) {
        if (eav == null) {
            reconnectEav();
            throw new EavException();
        }
        EavEntity e = eav.getEntityById(entityId);
        return eav.getAttributes(e);
    }
}
