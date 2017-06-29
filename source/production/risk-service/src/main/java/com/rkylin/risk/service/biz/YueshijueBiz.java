package com.rkylin.risk.service.biz;

import com.rkylin.risk.service.bean.PersonFactor;
import java.util.Map;

/**
 * 悦视觉
 *
 * @author
 * @create 2016-11-09 15:04
 **/
public interface YueshijueBiz {

  Map handleYueShijue(PersonFactor factor);

  Map getImagemap(PersonFactor factor);
}
