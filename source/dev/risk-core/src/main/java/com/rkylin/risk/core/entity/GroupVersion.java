package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GroupVersion implements Entity {
    private Short id;

    private Short groupid;

    private String groupname;

    private String grouptype;

    private Short createoperid;

    private String createopername;

    private Short issueoperid;

    private String issueopername;

    private String issueartifactid;

    private String issuegroupid;

    private String version;

    private String isissue;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}