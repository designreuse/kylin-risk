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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
    public void baiRongInfoQuery(@ModelAttribute CreditRequestEntity creditRequestEntity, @RequestParam boolean queryAgain, HttpSession httpSession, HttpServletResponse response) throws IOException {
        Authorization auth = (Authorization) httpSession.getAttribute("auth");

        CreditRequestEntity creditEntity = new CreditRequestEntity();
        creditEntity.setName(creditRequestEntity.getName());
        creditEntity.setMobile(creditRequestEntity.getMobile());
        creditEntity.setIdNumber(creditRequestEntity.getIdNumber());
        creditEntity.setChannel(creditRequestEntity.getChannel());
        creditEntity.setUserId(creditRequestEntity.getUserId());
        List<CreditRequestEntity> creditRequestEntityList = new ArrayList<>();
        creditRequestEntityList.add(creditEntity);

        CreditParam creditParam = new CreditParam();
        creditParam.setData(creditRequestEntityList);

        creditParam.setQuerier(auth.getRealname());
        creditParam.setQueryAgain(queryAgain);
        String result = creditBiz.requestProxy("/api/credit/bairong/ter?modules=SpecialList_c", creditParam);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.write(result);
        }
        response.setContentType("application/json");
    }
}




