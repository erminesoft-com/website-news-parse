package com.erminesoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "key")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "key_one")
    private String keyOne;

    @Column(name = "key_two")
    private String keyTwo;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "default_link")
    private String defaultLink;
}
