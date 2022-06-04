package com.example.SpringPos.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter @Setter
@NoArgsConstructor
public class Member {

    private Long id;

    @NotBlank(message = "필수 항목입니다.")
    private String name;

    @NotBlank(message = "필수 항목입니다.")
    @Email(message = "이메일 형태가 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "필수 항목입니다.")
    @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
    private String pwd;
    private String rank;

    public Member(String name, String email, String pwd, String rank) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.rank = rank;
    }
}
