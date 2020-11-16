package com.example.demo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DbUserDetails extends User {
	private final Account account;

	/**
	 * @param account
	 * @param authorities ユーザに付与する権限のリスト
	 */
	public DbUserDetails(Account account, Collection<GrantedAuthority> authorities) {

		super(account.getName(), account.getPassword(), true, true, true, true, authorities);

		this.account = account;

	}

	public Account getAccount() {
		return account;
	}
}
