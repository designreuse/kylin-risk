package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.LoanFundsLimit;

import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 11:24
 */
public interface LoanFundsLimitDao {

  LoanFundsLimit queryOne(Integer id);

  List<LoanFundsLimit> queryAll();

  List<LoanFundsLimit> queryLoanSales();

  List<LoanFundsLimit> queryRepayYears();

  void insert(LoanFundsLimit loanFundsLimit);

  void delete(Integer id);

  void update(LoanFundsLimit loanFundsLimit);

  void batchUpdateSalesByType(List<LoanFundsLimit> loanFundsLimitList);

  void batchUpdateLimitById(List<LoanFundsLimit> loanFundsLimitList);
}
