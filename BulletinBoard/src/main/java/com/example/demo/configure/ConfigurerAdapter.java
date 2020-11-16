package com.example.demo.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.InputData;

@Configuration
public class ConfigurerAdapter {

	// 外部設定値の取得
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String PROPERTY_DDL_AUTO;

	@Bean
	public InputData inputData() {
		return ("create".equals(this.PROPERTY_DDL_AUTO)) ? new InputData() : null;
	}
}