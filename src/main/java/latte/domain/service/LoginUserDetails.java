package latte.domain.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import latte.domain.model.User;

@SuppressWarnings("serial")
public class LoginUserDetails implements UserDetails{

	/**
	 * ユーザ
	 */
	private final User user;

	public LoginUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ロールに応じて権限を設定
		if (this.user.getRole().equals("ADMIN")) {
			return AuthorityUtils.createAuthorityList("AUTH_MEMBER", "AUTH_EVENT", "AUTH_LOCATION", "AUTH_CHARGE", "AUTH_USER");
		} else if (this.user.getRole().equals("E_ADMIN")) {
			return AuthorityUtils.createAuthorityList("AUTH_EVENT", "AUTH_USER");
		}
		return AuthorityUtils.createAuthorityList("AUTH_USER");
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
