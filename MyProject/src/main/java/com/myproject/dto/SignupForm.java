
package com.myproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupForm {

    @NotBlank(message = "로그인 ID를 입력해 주세요.")
    @Size(min = 4, max = 20,
          message = "로그인 ID는 4~20자로 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
             message = "로그인 ID는 영문과 숫자만 사용할 수 있습니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 72,
          message = "비밀번호는 8~72자로 입력해 주세요.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
