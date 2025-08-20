package com.sky.controller.admin;


import com.sky.mapper.OrderMapper;
import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计相关接口")
@Slf4j
public class ReportController {


    @Autowired
    private ReportService reportService;




    /**
     *
     * @description:营业额统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.result.Result<com.sky.vo.TurnoverReportVO>
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("营业额统计,{},{}", begin, end);
        TurnoverReportVO reportVO = reportService.getturnoverStatistics(begin,end);

        return Result.success(reportVO);

    }



    /**
     *
     * @description:营业额统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.result.Result<com.sky.vo.UserReportVO>
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> userReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("用户统计,{},{}", begin, end);
        UserReportVO userReportVO = reportService.getUserStatistics(begin,end);
        return Result.success(userReportVO);

    }




    /**
     *
     * @description:订单统计
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.result.Result<com.sky.vo.OrderReportVO>
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> orderReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("订单统计,{},{}", begin, end);
        OrderReportVO orderReportVO = reportService.getOrdersStatistics(begin,end);
        return Result.success(orderReportVO);

    }




    /**
     *
     * @description:查询销量排名top10
     * @author: Cvvvv
     * @param: [begin, end]
     * @return: com.sky.result.Result<com.sky.vo.SalesTop10ReportVO>
     */
    @GetMapping("/top10")
    @ApiOperation("查询销量排名top10")
    public Result<SalesTop10ReportVO> top10Report(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("查询销量排名top10,{},{}", begin, end);
        SalesTop10ReportVO top10 = reportService.getTop10(begin,end);
        return Result.success(top10);

    }



}
