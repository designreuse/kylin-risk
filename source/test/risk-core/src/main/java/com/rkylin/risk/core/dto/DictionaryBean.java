package com.rkylin.risk.core.dto;

import com.rkylin.risk.core.entity.DictionaryCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by 201508240185 on 2015/10/10.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DictionaryBean {
        private DictionaryCode dictionaryCode;

        private List<DictionaryCode> dictionaryCodes;
}
