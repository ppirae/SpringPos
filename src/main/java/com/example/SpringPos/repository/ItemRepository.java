package com.example.SpringPos.repository;

import com.example.SpringPos.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemRepository {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Item item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into ITEM (itemName, itemPrice, itemQuantity, itemStockTime) " +
                                "values (?, ?, ?, ?)",
                        new String[] { "ID" });
                pstmt.setString(1, item.getItemName());
                pstmt.setInt(2, item.getItemPrice());
                pstmt.setInt(3, item.getItemQuantity());
                pstmt.setTimestamp(4, item.getItemStockTime());
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        item.setId(keyValue.longValue());
    }

    public Item selectById(Long id) {
        List<Item> results = jdbcTemplate.query(
                "select * from ITEM where id = ?",
                new RowMapper<Item>() {
                    @Override
                    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Item item = new Item(
                                rs.getString("itemName"),
                                rs.getInt("itemPrice"),
                                rs.getInt("itemQuantity"),
                                rs.getTimestamp("itemStockTime"));
                        item.setId(rs.getLong("id"));
                        return item;
                    }
                }, id);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Item> selectAll() {
        List<Item> results = jdbcTemplate.query("select * from ITEM",
                (ResultSet rs, int rowNum) -> {
                    Item item = new Item(
                            rs.getString("itemName"),
                            rs.getInt("itemPrice"),
                            rs.getInt("itemQuantity"),
                            rs.getTimestamp("itemStockTime"));
                    item.setId(rs.getLong("id"));
                    return item;
                });
        return results;
    }

    public void update(Item item) {
        jdbcTemplate.update(
                "update ITEM set itemName = ?, itemPrice = ?, itemQuantity = ?, itemStockTime = ? where id = ?",
                item.getItemName(), item.getItemPrice(), item.getItemQuantity(), item.getItemStockTime(), item.getId());
    }
}
