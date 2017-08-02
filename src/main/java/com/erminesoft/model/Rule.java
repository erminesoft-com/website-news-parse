package com.erminesoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "strategy")
    private Integer Strategy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_id")
    private Key key;
}
