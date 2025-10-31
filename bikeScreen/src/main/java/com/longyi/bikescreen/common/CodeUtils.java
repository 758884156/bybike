package com.longyi.bikescreen.common;

import com.longyi.bikescreen.entity.Bike;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class CodeUtils {
    public static void main(String[] args) {
        System.out.println(orderCode());
    }
    public static String orderCode() {
        String str ="LY"+ System.currentTimeMillis();
        str += (int) ((Math.random() * 9 + 1) * 100);
        return str;
    }

    public static BigDecimal calacMoney(Integer useTime, Bike bike) {
        BigDecimal firstPrice = bike.getFirstPrice();
        BigDecimal price = bike.getPrice();

        if (useTime < bike.getFirstDistance()) {
            return firstPrice;
        } else {
            BigDecimal additionalTime = BigDecimal.valueOf(useTime - bike.getFirstDistance());
            BigDecimal additionalCost = additionalTime.multiply(price);
            return firstPrice.add(additionalCost);
        }
    }
    public static int calculateAndSetUseTime(LocalDateTime finishTime, LocalDateTime createTime) {
        // 计算时间差
        Duration duration = Duration.between(createTime, finishTime);
        // 获取秒差并转换为分钟差
        long secondsDifference = duration.getSeconds();
        int minutesDifference = (int) (secondsDifference / 60);

        return minutesDifference; // 返回分钟差
    }
}