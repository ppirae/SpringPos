package com.example.SpringPos.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Getter @Setter
@NoArgsConstructor
public class Sales {

    private Long id;

    @NotNull(message = "필수 항목입니다.")
    private Long itemId;
    private String itemName;
    private Integer itemPrice;

    @NotNull(message = "필수 항목입니다.")
    private Integer saleQuantity;
    private Integer totalPrice;
    private Timestamp SalesTime;

    public Sales(Long itemId, String itemName, Integer itemPrice, Integer saleQuantity, Integer totalPrice, Timestamp salesTime) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.saleQuantity = saleQuantity;
        this.totalPrice = totalPrice;
        this.SalesTime = salesTime;
    }
}
