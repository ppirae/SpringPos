package com.example.SpringPos.repository;

import com.example.SpringPos.domain.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class SalesRepository {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public SalesRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Sales sales) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into SALES (itemId, itemName, itemPrice, saleQuantity, totalPrice, salesTime) " +
                                "values (?, ?, ?, ?, ?, ?)",
                        new String[] { "id" });
                pstmt.setLong(1, sales.getItemId());
                pstmt.setString(2, sales.getItemName());
                pstmt.setInt(3, sales.getItemPrice());
                pstmt.setInt(4, sales.getSaleQuantity());
                pstmt.setInt(5, sales.getTotalPrice());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        sales.setId(keyValue.longValue());
    }

    public List<Sales> selectAll() {
        List<Sales> results = jdbcTemplate.query("select * from SALES",
                (ResultSet rs, int rowNum) -> {
                    Sales sales = new Sales(
                            rs.getLong("itemId"),
                            rs.getString("itemName"),
                            rs.getInt("itemPrice"),
                            rs.getInt("saleQuantity"),
                            rs.getInt("totalPrice"),
                            rs.getTimestamp("salesTime"));
                    sales.setId(rs.getLong("id"));
                    return sales;
                });
        return results;
    }

    public Sales selectBySaleId(Long id) {
        List<Sales> results = jdbcTemplate.query(
                "select * from SALES where id = ?",
                new RowMapper<Sales>() {
                    @Override
                    public Sales mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Sales sales = new Sales(
                                rs.getLong("itemId"),
                                rs.getString("itemName"),
                                rs.getInt("itemPrice"),
                                rs.getInt("saleQuantity"),
                                rs.getInt("totalPrice"),
                                rs.getTimestamp("salesTime"));
                        sales.setId(rs.getLong("id"));
                        return sales;
                    }
                }, id);

        return results.isEmpty() ? null : results.get(0);
    }
}
