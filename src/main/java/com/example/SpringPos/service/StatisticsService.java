package com.example.SpringPos.service;

import com.example.SpringPos.domain.Sales;
import com.example.SpringPos.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatisticsService {

    private final SalesRepository salesRepository;

    @Autowired
    public StatisticsService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    //하루 매출액 구하는 메서드
    public int totalSalePriceToday() {
        int totalSalePrice = 0;
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<Sales> saleList = salesRepository.selectAll();
        if (saleList.size() == 0) {
            System.out.println("판매된 상품이 없습니다.");
            totalSalePrice = 0;
        } else {
            for (Sales sale : saleList) {
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" , Locale.KOREA );
                String str = sdf.format(sale.getSalesTime());
                if (now.equals(str)) {
                    totalSalePrice += sale.getTotalPrice();
                }
            }
        }
        return totalSalePrice;
    }

    //이번주 매출액 구하는 메서드
    public int totalSalePriceThisWeek() {
        int totalSalePrice = 0;
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        int nowWeek = getWeekOfYear(now);

        List<Sales> saleList = salesRepository.selectAll();
        if (saleList.size() == 0) {
            System.out.println("판매된 상품이 없습니다.");
            totalSalePrice = 0;
        } else {
            for (Sales sale : saleList) {
                Timestamp salesWeek = sale.getSalesTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String salesWeekFormat = sdf.format(salesWeek);
                if (nowWeek == getWeekOfYear(salesWeekFormat)) {
                    totalSalePrice += sale.getTotalPrice();
                }
            }
        }
        return totalSalePrice;
    }

    //이번달 매출액 구하는 메서드
    public int totalSalePriceThisMonth() {
        int totalSalePrice = 0;
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String[] split = now.split("-");
        String nowMonth = split[1];

        List<Sales> saleList = salesRepository.selectAll();
        if (saleList.size() == 0) {
            System.out.println("판매된 상품이 없습니다.");
            totalSalePrice = 0;
        } else {
            for (Sales sale : saleList) {
                Timestamp salesWeek = sale.getSalesTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String salesWeekFormat = sdf.format(salesWeek);
                String[] findSplit = salesWeekFormat.split("-");
                String findMonth = findSplit[1];
                if (nowMonth.equals(findMonth)) {
                    totalSalePrice += sale.getTotalPrice();
                }
            }
        }
        return totalSalePrice;
    }

    //총 매출액 구하는 메서드
    public int totalSalePriceAll() {
        int totalSalePrice = 0;
        List<Sales> saleList = salesRepository.selectAll();
        if (saleList.size() == 0) {
            System.out.println("판매된 상품이 없습니다.");
            totalSalePrice = 0;
        } else {
            for (Sales sale : saleList) {
                totalSalePrice += sale.getTotalPrice();
            }
        }
        return totalSalePrice;
    }

    //최다 판매 상품 이름 구하는 메서드
    public String bestSaleItemName() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        List<Sales> saleList = salesRepository.selectAll();
        String maxKey = null;

        if (saleList.size() == 0) {
            return null;
        } else {
            for (Sales sale : saleList) {
                Integer integer = map.get(sale.getItemName());
                if (integer == null) {
                    map.put(sale.getItemName(), sale.getSaleQuantity());
                } else {
                    map.put(sale.getItemName(), integer + sale.getSaleQuantity());
                }
            }

            int max = Collections.max(map.values());

            for (String key : map.keySet()) {
                if (map.get(key) == max) {
                    maxKey = key;
                }
            }

            for (Sales sale : saleList) {
                if (sale.getItemName() == maxKey) {
                    return sale.getItemName();
                }
            }
            return null;
        }
    }

    //최다 판매 상품 가격 구하는 메서드
    public int bestSaleItemTotalPrice() {
        List<Sales> saleList = salesRepository.selectAll();
        int totalPrice = 0;

        String bestItemName = bestSaleItemName();
        for (Sales sale : saleList) {
            if (sale.getItemName().equals(bestItemName)) {
                totalPrice += sale.getTotalPrice();
            }
        }
        return totalPrice;
    }

    //최다 판매 상품 개수 구하는 메서드
    public int bestSaleItemTotalCount() {
        List<Sales> saleList = salesRepository.selectAll();
        int totalCount = 0;

        String bestItemName = bestSaleItemName();
        for (Sales sale : saleList) {
            if (sale.getItemName().equals(bestItemName)) {
                totalCount += sale.getSaleQuantity();
            }
        }
        return totalCount;
    }


    //최고 금액 판매 상품 이름 구하는 메서드
    public String bestMoneyItemName() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        List<Sales> saleList = salesRepository.selectAll();
        String maxKey = null;

        if (saleList.size() == 0) {
            return null;
        } else {
            for (Sales sale : saleList) {
                Integer integer = map.get(sale.getItemName());
                if (integer == null) {
                    map.put(sale.getItemName(), sale.getTotalPrice());
                } else {
                    map.put(sale.getItemName(), integer + sale.getTotalPrice());
                }
            }

            int max = Collections.max(map.values());

            for (String key : map.keySet()) {
                if (map.get(key) == max) {
                    maxKey = key;
                }
            }

            for (Sales sale : saleList) {
                if (sale.getItemName() == maxKey) {
                    return sale.getItemName();
                }
            }
            return null;
        }
    }

    //최고 금액 판매 상품 가격 구하는 메서드
    public int bestMoneyItemTotalPrice() {
        List<Sales> saleList = salesRepository.selectAll();
        int totalPrice = 0;

        String bestMoneyItemName = bestMoneyItemName();
        for (Sales sale : saleList) {
            if (sale.getItemName().equals(bestMoneyItemName)) {
                totalPrice += sale.getTotalPrice();
            }
        }
        return totalPrice;
    }

    //최고 금액 판매 상품 개수 구하는 메서드
    public int bestMoneyItemTotalCount() {
        List<Sales> saleList = salesRepository.selectAll();
        int totalCount = 0;

        String bestMoneyItemName = bestMoneyItemName();
        for (Sales sale : saleList) {
            if (sale.getItemName().equals(bestMoneyItemName)) {
                totalCount += sale.getSaleQuantity();
            }
        }
        return totalCount;
    }

    //이번주가 이번년도의 몇번째 주차인지 구하는 메서드
    public int getWeekOfYear(String date) {
        Calendar calendar = Calendar.getInstance();
        String[] dates = date.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);
        calendar.set(year, month - 1, day);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
