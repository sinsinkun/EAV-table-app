package org.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@Table(name = "eav_entities")
public class EavEntity {
    @Column(name = "id", unique = true, nullable = false)
    private int id = 0;

    @Column(name = "created_at")
    private String createdAt = null;

    @Column(name = "entity")
    private String entity = null;

    @Column(name = "entity_type_id", nullable = false)
    private int entityTypeId = 0;
}
