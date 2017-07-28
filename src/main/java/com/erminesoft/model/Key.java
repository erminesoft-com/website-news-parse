package com.erminesoft.model;

import javax.persistence.*;

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

    public Key() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyOne() {
        return keyOne;
    }

    public void setKeyOne(String keyOne) {
        this.keyOne = keyOne;
    }

    public String getKeyTwo() {
        return keyTwo;
    }

    public void setKeyTwo(String keyTwo) {
        this.keyTwo = keyTwo;
    }

    public String getDefaultLink() {
        return defaultLink;
    }

    public void setDefaultLink(String defaultLink) {
        this.defaultLink = defaultLink;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", keyOne='" + keyOne + '\'' +
                ", keyTwo='" + keyTwo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (keyOne != null ? !keyOne.equals(key.keyOne) : key.keyOne != null) return false;
        return keyTwo != null ? keyTwo.equals(key.keyTwo) : key.keyTwo == null;
    }

    @Override
    public int hashCode() {
        int result = keyOne != null ? keyOne.hashCode() : 0;
        result = 31 * result + (keyTwo != null ? keyTwo.hashCode() : 0);
        return result;
    }
}
