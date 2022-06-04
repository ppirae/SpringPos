package com.example.SpringPos.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "필수 항목입니다.")
    private String email;

    @NotBlank(message = "필수 항목입니다.")
    private String pwd;
}
