package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordEditForm {
	@NotBlank(message = "新しいパスワードを入力してください。")
	@Length(min = 8, message = "新しいパスワードは8文字以上で入力してください。")
	private String password;

	@NotBlank(message = "新しいパスワード（確認用）を入力してください。")
	private String passwordConfirmation;
}
