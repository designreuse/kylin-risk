package com.rkylin.risk.boss.restController;


import com.rkylin.risk.boss.biz.CreditBiz;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.dto.CreditParam;
import com.rkylin.risk.core.dto.CreditRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2016-12-14 14:26
 */
@RestController
@RequestMapping("api/1/bairong")
@Slf4j
public class BaiRongInfoRestAction {
    @Resource
    private CreditBiz creditBiz;

    @RequestMapping("queryBaiRong")
    public String baiRongInfoQuery(@ModelAttribute CreditRequestEntity creditRequestEntity, @RequestParam boolean queryAgain, HttpServletRequest request) throws Exception {
        Authorization auth = (Authorization) request.getSession().getAttribute("auth");
        CreditRequestEntity creditEntity = new CreditRequestEntity();
        creditEntity.setName(creditRequestEntity.getName());
        creditEntity.setMobile(creditRequestEntity.getMobile());
        creditEntity.setIdNumber(creditRequestEntity.getIdNumber());
        List<CreditRequestEntity> creditRequestEntityList = new ArrayList<>();
        creditRequestEntityList.add(creditEntity);

        CreditParam creditParam = new CreditParam();
        creditParam.setData(creditRequestEntityList);

        creditParam.setQuerier(auth.getRealname());
        creditParam.setQueryAgain(queryAgain);
        return creditBiz.requestProxy("api/credit/bairong/ter?modules=SpecialList_c", creditParam);

    }


}
