package com.example.schedulerv2.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하여야 합니다.")
    private final String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다.")
    private final String password;

    public UserUpdateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
