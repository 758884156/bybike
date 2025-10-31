package com.longyi.bikescreen.controller;

import com.longyi.bikescreen.common.QueryPageParam;
import com.longyi.bikescreen.common.Result;
import com.longyi.bikescreen.entity.Bikeorder;
import com.longyi.bikescreen.entity.ChartLine;
import com.longyi.bikescreen.entity.ChartPie;
import com.longyi.bikescreen.entity.Platform;
import com.longyi.bikescreen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/echart")
public class EchartController {
    @Autowired
    private BikeService bikeService;
    @Autowired
    private InvertService invertService;
    @Autowired
    private RecoveryService recoveryService;
    @Autowired
    private BikeorderService bikeorderService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private PlatformService platformService;

    @GetMapping("/pie1")
    public Result biketype() {
        List<ChartPie> data = bikeService.biketype();
        return Result.suc(data);
    }

    @GetMapping("/pie2")
    public Result weekmoney() {
        String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        List<ChartPie> orderData = bikeorderService.weekMoney("0");
        List<ChartPie> incomeData = incomeService.weekMoney("0");
        System.out.println(orderData);
        System.out.println(incomeData);
        List<ChartLine> record = new ArrayList();
        for (int i = 0; i < 7; i++) {
            ChartLine line = new ChartLine();
            line.setName(weekdays[i]);
            line.setValue1(0);
            line.setValue2(0);
            for (ChartPie chartPie : orderData) {
                if (chartPie.getName().equals(weekdays[i])) {
                    line.setValue1(chartPie.getValue());
                    break;
                }
            }
            for (ChartPie chartPie : incomeData) {
                if (chartPie.getName().equals(weekdays[i])) {
                    line.setValue2(chartPie.getValue());
                    break;
                }
            }
            record.add(line);
        }

        return Result.suc(record);
    }

    @GetMapping("/platformList")
    public List<Platform> platformList() {
        return platformService.lambdaQuery().orderByDesc(Platform::getProperty).list();
    }

    @GetMapping("/bikeInvert/{province}")
    public List<ChartPie> bikeInvert(@PathVariable String province) {
        return invertService.bikeInvert(province);
    }

    @GetMapping("/bikeRecovery/{province}")
    public List<ChartPie> bikeRecovery(@PathVariable String province) {
        return recoveryService.bikeRecovery(province);
    }

    @GetMapping("/bikeUser")
    public List<ChartPie> bikeUser() {
        return bikeorderService.bikeUser();
    }

    @GetMapping("/bikeOrder")
    public List<ChartPie> bikeOrder() {
        return bikeorderService.bikeOrder();
    }

    @GetMapping("/bikeIncome")
    public List<ChartPie> bikeIncome() {
        return incomeService.bikeIncome();
    }

    @PostMapping("/orderChart")
    public Result orderChart(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String bike = (String) param.get("bike");
        String time = (String) param.get("time");
        String record = (String) param.get("record");
        List<ChartPie> chart = new ArrayList<>();
        if (record.equals("0")) {
            if (time.equals("0")) {
                chart = bikeorderService.allMoney(bike);
            }
            if (time.equals("1")) {
                chart = incomeService.yearMoney(bike);
            }
            if (time.equals("2")) {
                chart = incomeService.monthMoney(bike);

            }
            if (time.equals("3")) {
                chart = incomeService.weekMoney(bike);
            }

        } else if (record.equals("1")) {
            if (time.equals("0")) {
                chart = bikeorderService.allCount(bike);
            }
            if (time.equals("1")) {
                chart = bikeorderService.yearCount(bike);
            }
            if (time.equals("2")) {
                chart = bikeorderService.monthCount(bike);
            }
            if (time.equals("3")) {
                chart = bikeorderService.weekCount(bike);
            }
        } else if (record.equals("2")) {
            if (time.equals("0")) {
                chart = incomeService.allMoney(bike);
            }
            if (time.equals("1")) {
                chart = incomeService.yearMoney(bike);
            }
            if (time.equals("2")) {
                chart = incomeService.monthMoney(bike);
            }
            if (time.equals("3")) {
                chart = incomeService.weekMoney(bike);
            }
        }
        return Result.suc(chart);
    }
    @PostMapping("/bikeOrderList")
    public List<Bikeorder> bikeOrder(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String time = (String) param.get("time");
        List<Bikeorder> list = new ArrayList<>();
        LocalDate now = LocalDate.now();
        if (time.equals("0")) {
            list = bikeorderService.lambdaQuery().orderByDesc(Bikeorder::getCreateTime).list();
        }
        if (time.equals("1")) {
            LocalDate startOfYear = now.with(TemporalAdjusters.firstDayOfYear());
            LocalDateTime startOfYearDateTime = startOfYear.atStartOfDay();
            list = bikeorderService.lambdaQuery()
                    .ge(Bikeorder::getCreateTime, startOfYearDateTime)
                    .orderByDesc(Bikeorder::getCreateTime)
                    .list();
        }
        if (time.equals("2")) {
            LocalDate startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
            LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
            list = bikeorderService.lambdaQuery()
                    .ge(Bikeorder::getCreateTime, startOfMonthDateTime)
                    .orderByDesc(Bikeorder::getCreateTime)
                    .list();
        }
        if (time.equals("3")) {
            LocalDate startOfWeek = now.with(DayOfWeek.MONDAY);
            LocalDateTime startOfWeekDateTime = startOfWeek.atStartOfDay();
            list = bikeorderService.lambdaQuery()
                    .ge(Bikeorder::getCreateTime, startOfWeekDateTime)
                    .orderByDesc(Bikeorder::getCreateTime)
                    .list();
        }
        return list;
    }
    @GetMapping("/bikeOrderIncomeChart")
    public List<ChartLine> bikeOrderIncomeChart() {
        List<ChartPie> orderchart =bikeorderService.bikeOrderCount();
        List<ChartPie> incomechart = incomeService.bikeIncomeCount();
        return getCombinedCharts(orderchart,incomechart);
    }
    private List<ChartLine> getCombinedCharts(List<ChartPie> orderchart, List<ChartPie> incomechart) {
        List<ChartLine> charts = new ArrayList<>();
        List<String> name1 = orderchart.stream().map(ChartPie::getName).collect(Collectors.toList());
        List<String> name2 = incomechart.stream().map(ChartPie::getName).collect(Collectors.toList());
        LinkedHashSet<String> nameSet = new LinkedHashSet<>();
        nameSet.addAll(name1);
        nameSet.addAll(name2);
        List<String> name = new ArrayList<>(nameSet);
        for (int i = 0; i < name.size(); i++) {
            ChartLine chart = new ChartLine();
            chart.setName(name.get(i));
            chart.setValue1(0);
            chart.setValue2(0);
            for (ChartPie chartPie : orderchart) {
                if (chartPie.getName().equals(name.get(i)))
                    chart.setValue1(chartPie.getValue());

            }
            for (ChartPie chartPie : incomechart) {
                if (chartPie.getName().equals(name.get(i)))
                    chart.setValue2(chartPie.getValue());

            }
            charts.add(chart);
        }
        return charts;
    }
    @PostMapping("/orderIncomeChart")
    public Result orderIncomeChart(@RequestBody QueryPageParam query) {
        System.out.println(query);
        HashMap param = query.getParam();
        String time = (String) param.get("time");
        List<ChartPie> orderchart = new ArrayList<>();
        List<ChartPie> incomechart = new ArrayList<>();

        if (time.equals("0")) {
            orderchart = bikeorderService.allMoney("0");
            incomechart = incomeService.allMoney("0");
        }
        if (time.equals("1")) {
            orderchart = bikeorderService.yearMoney("0");
            incomechart = incomeService.yearMoney("0");
        }
        if (time.equals("2")) {
            orderchart = bikeorderService.monthMoney("0");
            incomechart = incomeService.monthMoney("0");
        }
        if (time.equals("3")) {
            orderchart = bikeorderService.weekMoney("0");
            incomechart = incomeService.weekMoney("0");
        }
        List<ChartLine> combinedCharts = getCombinedCharts(orderchart, incomechart);
        return Result.suc(combinedCharts);

    }
    @GetMapping("/bikeCity")
    public List<ChartPie> bikeCity() {
        return invertService.bikeCity();
    }
    @PostMapping("/mapChart")
    public Result mapChart() {
        return Result.suc(invertService.mapChart());
    }
}