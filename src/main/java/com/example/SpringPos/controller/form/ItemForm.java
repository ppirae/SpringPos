package com.example.SpringPos.controller.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class ItemForm {

    private Long id;

    @NotBlank(message = "필수 항목입니다.")
    private String itemName;

    @NotNull(message = "필수 항목입니다.")
    @Min(value = 100, message = "최소 100원 이상이어야 합니다.")
    private int itemPrice;

    @NotNull(message = "필수 항목입니다.")
    @Min(value = 1, message = "최소 한개 이상이어야 합니다.")
    private int itemQuantity;

    private Timestamp itemStockTime;
}
