package com.erminesoft.model;

import javax.persistence.*;

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

    public Rule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Integer getStrategy() {
        return Strategy;
    }

    public void setStrategy(Integer strategy) {
        Strategy = strategy;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
