package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.LoanFundsRepayYear;

import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 11:24
 */
public interface LoanFundsRepayYearDao {

    LoanFundsRepayYear queryOne(Integer id);

    List<LoanFundsRepayYear> queryAll();

    List<LoanFundsRepayYear> queryLoanSales();

    void insert(LoanFundsRepayYear loanFundsRepayYear);

    void delete(Integer id);

    void update(LoanFundsRepayYear loanFundsRepayYear);

    void batchUpdateByType(List<LoanFundsRepayYear> loanFundsRepayYearList);

    void batchUpdateById(List<LoanFundsRepayYear> loanFundsRepayYearList);

}
