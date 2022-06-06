package com.example.SpringPos.service;

import com.example.SpringPos.domain.Item;
import com.example.SpringPos.domain.Sales;
import com.example.SpringPos.repository.ItemRepository;
import com.example.SpringPos.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SalesService {

    private final ItemRepository itemRepository;
    private final SalesRepository salesRepository;

    @Autowired
    public SalesService(ItemRepository itemRepository, SalesRepository salesRepository) {
        this.itemRepository = itemRepository;
        this.salesRepository = salesRepository;
    }

    public Long saveSales(Long itemId, int count) {

        Item findItem = itemRepository.selectById(itemId);
        int stockQuantity = findItem.getItemQuantity();

        if (stockQuantity < count) {
            return null;
        }

        Sales sales = new Sales();
        sales.setItemId(itemId);
        sales.setItemName(findItem.getItemName());
        sales.setItemPrice(findItem.getItemPrice());
        sales.setSaleQuantity(count);
        sales.setTotalPrice(findItem.getItemPrice() * count);
        sales.setSalesTime(new Timestamp(System.currentTimeMillis()));

        salesRepository.insert(sales);

        findItem.setItemQuantity(findItem.getItemQuantity()-count);
        itemRepository.update(findItem);

        return sales.getId();
    }

    public List<Sales> findSales() {
        return salesRepository.selectAll();
    }

    public Sales findOne(Long saleId) {
        return salesRepository.selectBySaleId(saleId);
    }
}
