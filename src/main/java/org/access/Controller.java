package org.access;

import org.database.*;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method=RequestMethod.POST, path="/login")
    public ResponseEntity<?> login(@RequestBody DbAccess auth) {
        DbSetup setup = new DbSetup();
        setup.server = auth.getHost();
        setup.dbName = auth.getDbName();
        setup.user = auth.getUser();
        setup.password = auth.getPassword();
        if (!setup.isValid()) {
            return ResponseEntity.status(400).body("Missing required info");
        }
        try {
            eav = new EavInterface(setup);
            Fn.printColor(AnsiColors.GREEN, "Connected to DB");
            return ResponseEntity.status(200).body("OK");
        } catch(Exception e) {
            Fn.printColor(AnsiColors.RED, "Err: Could not connect to DB -- " + e.getMessage());
            return ResponseEntity.status(200).body("ERR");
        }
    }

    @RequestMapping(method=RequestMethod.GET, path="/")
    public String index() {
        return "Hello world from SpringBoot";
    }

    @RequestMapping(method=RequestMethod.GET, path="/view/all")
    public List<EavView> getAll() {
        if (eav == null) {
            throw new EavException();
        }
        return eav.getEverything();
    }

    @RequestMapping(method=RequestMethod.GET, path="/view/entities")
    public List<EavView> getViewEntities() {
        if (eav == null) {
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
            throw new EavException();
        }
        return eav.getEntityTypes();
    }

    @RequestMapping(method=RequestMethod.GET, path="/entities")
    public List<EavEntity> getAllEntities() {
        if (eav == null) {
            throw new EavException();
        }
        return eav.getEntities();
    }

    @RequestMapping(method=RequestMethod.GET, path="/entities/{type_id}")
    public List<EavEntity> getEntitiesForType(@PathVariable("type_id") Integer typeId) {
        if (eav == null) {
            throw new EavException();
        }
        EavEntityType et = eav.getEntityTypeById(typeId);
        return eav.getEntities(et);
    }

    @RequestMapping(method=RequestMethod.GET, path="/attributes")
    public List<EavAttribute> getAllAttrs() {
        if (eav == null) {
            throw new EavException();
        }
        return eav.getAttributes();
    }

    @RequestMapping(method=RequestMethod.GET, path="/attributes/{entity_id}")
    public List<EavAttribute> getAttrsForEntity(@PathVariable("entity_id") Integer entityId) {
        if (eav == null) {
            throw new EavException();
        }
        EavEntity e = eav.getEntityById(entityId);
        return eav.getAttributes(e);
    }
}
