package com.erminesoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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
}
