package com.sky.service;


import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {


    /**
     *
     * @description:营业额统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.vo.TurnoverReportVO
     */
    TurnoverReportVO getturnoverStatistics(LocalDate begin, LocalDate end);

    /**
     *
     * @description:用户统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.vo.UserReportVO
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     *
     * @description:订单统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.vo.OrderReportVO
     */
    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    /**
     *
     * @description:查询销量排名top10
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.vo.SalesTop10ReportVO
     */
    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);
}
