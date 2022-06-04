package com.example.SpringPos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class Item {

    private Long id;

    @NotBlank(message = "필수 항목입니다.")
    private String itemName;

    @NotNull(message = "필수 항목입니다.")
    @Min(value = 100, message = "최소 100원 이상이어야 합니다.")
    private Integer itemPrice;

    @NotNull(message = "필수 항목입니다.")
    @Min(value = 1, message = "최소 한개 이상이어야 합니다.")
    private Integer itemQuantity;

    private Timestamp itemStockTime;

    public Item(String itemName, Integer itemPrice, Integer itemQuantity, Timestamp itemStockTime) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemStockTime = itemStockTime;
    }
}
