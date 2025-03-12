package com.blogpessoal.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
    
     //Constante = um SHA 256 que será a chave de codificação do token     
	public static final String SECRET = "c528f7d2cd36e994108442c6f1a72f0989ac99d484e8c193035a33780428dee9";

	//chave secret foi codificado pelo base64
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// aqui o método pega tudo do token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}

	//daqui pra baixo ele extrai os claims = informações ocultas do token
	// o T é de TYPE, ou seja, o TIPO do dado (que não foi definido)
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	//pega a data de expiração do token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	//valida se o token tá vencido
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	//compara os dados acima com o token em si
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//construção do token a partir dos dados informados
	/*
	 * data atual + 100 (milissegundos) * 60 (minutos) * 60 (minutos) -> tempo de validade do token
	 */
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	//criação do token
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

}