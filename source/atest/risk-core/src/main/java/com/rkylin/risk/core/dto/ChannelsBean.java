package com.rkylin.risk.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by 201508240185 on 2015/10/12.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ChannelsBean {
        private List<ChannelBean> channels;
}
