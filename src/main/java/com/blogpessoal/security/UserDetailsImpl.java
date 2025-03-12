package com.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	// controle de versão para essa classe

	private String userName;// e-mail
	private String password;//senha
	private List<GrantedAuthority> authorities;
	//autorizações que o usuário possui

	/*método construtor - 
	 * o UserDetailsImpl pega os dados do usuário, valida e depois
	 * manda pro usuario na model
	 */
	 public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}

	public UserDetailsImpl() {	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	//pega a senha
	@Override
	public String getPassword() {

		return password;
	}

	//pega o email
	@Override
	public String getUsername() {

		return userName;
	}

	// verifica se o acesso expirou
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
    
	//verifica se o usuário está bloqueado
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
    //valida se as credenciais estão expiradas
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//verifica se o usuário está ativo
	@Override
	public boolean isEnabled() {
		return true;
	}

}
