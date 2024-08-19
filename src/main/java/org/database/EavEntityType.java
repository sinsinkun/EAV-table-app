package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Table(name = "eav_entity_types")
public class EavEntityType {

    @Column(name = "id", unique = true, nullable = false)
    private int id = 0;

    @Column(name = "created_at")
    private String createdAt = null;

    @Column(name = "entity_Type")
    private String entityType = null;
}
