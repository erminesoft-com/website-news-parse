package com.erminesoft.model;

import javax.persistence.*;

@Entity
@Table(name = "web_site")
public class WebSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "site")
    private String site;

    @Column(name = "strategy")
    private int strategy;

    @Column(name = "name")
    private String name;

    @Column(name = "key_one")
    private String keyFirst;

    @Column(name = "key_two")
    private String keySecond;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title")
    private Rule title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link")
    private Rule link;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image")
    private Rule image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "description")
    private Rule desc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time")
    private Rule date;

    @Column(name = "title_enable")
    private boolean titleEnable;

    @Column(name = "link_enable")
    private boolean linkEnable;

    @Column(name = "image_enable")
    private boolean imageEnable;

    @Column(name = "desc_enable")
    private boolean descEnable;

    @Column(name = "time_enable")
    private boolean timeEnable;

    public WebSite() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public String getKeyFirst() {
        return keyFirst;
    }

    public void setKeyFirst(String keyFirst) {
        this.keyFirst = keyFirst;
    }

    public String getKeySecond() {
        return keySecond;
    }

    public void setKeySecond(String keySecond) {
        this.keySecond = keySecond;
    }

    public Rule getTitle() {
        return title;
    }

    public void setTitle(Rule title) {
        this.title = title;
    }

    public Rule getLink() {
        return link;
    }

    public void setLink(Rule link) {
        this.link = link;
    }

    public Rule getImage() {
        return image;
    }

    public void setImage(Rule image) {
        this.image = image;
    }

    public Rule getDesc() {
        return desc;
    }

    public void setDesc(Rule desc) {
        this.desc = desc;
    }

    public Rule getDate() {
        return date;
    }

    public void setDate(Rule date) {
        this.date = date;
    }

    public boolean isTitleEnable() {
        return titleEnable;
    }

    public void setTitleEnable(boolean titleEnable) {
        this.titleEnable = titleEnable;
    }

    public boolean isLinkEnable() {
        return linkEnable;
    }

    public void setLinkEnable(boolean linkEnable) {
        this.linkEnable = linkEnable;
    }

    public boolean isImageEnable() {
        return imageEnable;
    }

    public void setImageEnable(boolean imageEnable) {
        this.imageEnable = imageEnable;
    }

    public boolean isDescEnable() {
        return descEnable;
    }

    public void setDescEnable(boolean descEnable) {
        this.descEnable = descEnable;
    }

    public boolean isTimeEnable() {
        return timeEnable;
    }

    public void setTimeEnable(boolean timeEnable) {
        this.timeEnable = timeEnable;
    }

    @Override
    public String toString() {
        return "WebSite{" +
                "id=" + id +
                ", site='" + site + '\'' +
                ", strategy=" + strategy +
                ", keyFirst='" + keyFirst + '\'' +
                ", keySecond='" + keySecond + '\'' +
                ", title=" + title +
                ", link=" + link +
                ", image=" + image +
                ", desc=" + desc +
                ", date=" + date +
                ", titleEnable=" + titleEnable +
                ", linkEnable=" + linkEnable +
                ", imageEnable=" + imageEnable +
                ", descEnable=" + descEnable +
                ", timeEnable=" + timeEnable +
                '}';
    }
}
