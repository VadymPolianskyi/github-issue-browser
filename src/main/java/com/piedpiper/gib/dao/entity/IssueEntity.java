package com.piedpiper.gib.dao.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issue")
@AllArgsConstructor
@NoArgsConstructor
public class IssueEntity {
    @Id
    private String id;

    @Column(name = "name")
    private String name;
}
