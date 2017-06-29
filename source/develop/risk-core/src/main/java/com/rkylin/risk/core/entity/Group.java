package com.rkylin.risk.core.entity;

import java.util.List;
import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group implements Entity {
    private Short id;

    private String groupname;

    private String grouptype;

    private LocalDate effdate;

    private LocalDate expdate;

    private String status;

    private Short createoperid;

    private String createopername;

    private String issueartifactid;

    private String issuegroupid;

    private String version;

    private String isexecute;

    private DateTime createtime;

    private DateTime updatetime;

    private List<GroupChannel> groupChannels;

    private static final long serialVersionUID = 1L;
}