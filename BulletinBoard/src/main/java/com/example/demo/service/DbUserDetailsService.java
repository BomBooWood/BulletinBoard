package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Account;
import com.example.demo.DbUserDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class DbUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository u_repos;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// not user found ここ
		User u = u_repos.findByUsername(username);
		Account account = new Account(u.getUsername(), u.getEncodedPassword());

		return new DbUserDetails(account, getAuthorities(account));
	}

	/**
	 * 認証が通った時にこのユーザに与える権限の範囲を設定する。
	 * 
	 * @param account DBから取得したユーザ情報。
	 * @return 権限の範囲のリスト。
	 */
	private Collection<GrantedAuthority> getAuthorities(Account account) {
		// 認証が通った時にユーザに与える権限の範囲を設定する。
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}
}
