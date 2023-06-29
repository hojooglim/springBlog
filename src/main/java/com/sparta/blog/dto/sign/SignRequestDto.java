package com.sparta.blog.dto.sign;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignRequestDto {
    @NotNull(message = "username은 필수 입니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하의 숫자를 입력하세요")
    private String username;

    @NotNull(message = "password 필수 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)만 입력 가능합니다.")
    @Size(min=7,max=16)
    private String password;

    @NotNull(message = "email 필수 입니다.")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+",message = "email 형식이 맞지 않습니다.")
    private String email;

}
