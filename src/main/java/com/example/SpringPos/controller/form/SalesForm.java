package com.example.SpringPos.controller.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SalesForm {

    @NotNull(message = "필수 항목입니다.")
    private Long id;

    @NotNull(message = "필수 항목입니다.")
    @Min(1)
    private int count;

    //private String payment;
}
