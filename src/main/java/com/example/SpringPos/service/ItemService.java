package com.example.SpringPos.service;

import com.example.SpringPos.domain.Item;
import com.example.SpringPos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.insert(item);
    }

    public void updateItem(Long itemId, String itemName, int itemPrice, int itemQuantity) {
        Item findItem = itemRepository.selectById(itemId);
        findItem.setItemName(itemName);
        findItem.setItemPrice(itemPrice);
        findItem.setItemQuantity(itemQuantity);
        findItem.setItemStockTime(Timestamp.valueOf(LocalDateTime.now()));
        itemRepository.update(findItem);
    }

    public List<Item> findItems() {
        return itemRepository.selectAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.selectById(itemId);
    }
}
