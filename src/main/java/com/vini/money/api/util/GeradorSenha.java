package com.vini.money.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Gerador de senha criptografada.
 * @author vinicius
 *
 */
public class GeradorSenha {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		System.out.println(encode.encode("admin"));
	}
}
