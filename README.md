# EAV-table-design

Run the setup script to setup a local database with a basic Entity Attribute Value (EAV) system

- Includes utility stored procedures for ease of use
- Includes utility views for ease of use

### Adding entries
1. create entity/entity type
`call create_eav_entity(entity_name, entity_type);`

2. create attribute linked to entity type
`call create_eav_attr(attr_name, attr_type, entity_type_id, allow_multiple);`

3. (optional) view attribute definitions for existing entity types
`select * from eav_schema_definitions;`

4. create value for entity/attribute
`call create_eav_value(entity_id, attr_id, ...value);`

5. (optional) view all existing entities, all possible attributes per entity, and values if exist
`select * from all_possible_eav_data;`

6. (optional) view only values inserted into the EAV table
`select * from all_existing_eav_data;`
