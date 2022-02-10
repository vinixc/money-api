package com.vini.money.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.vini.money.api.config.token.CustomTokenEnhancer;

@Configuration
@EnableAuthorizationServer
@Profile("oauth-security")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("@ngular")
			.scopes("read","write")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(20)
			.refreshTokenValiditySeconds(3600 * 24)
		.and()
			.withClient("mobile")
			.secret("mobile")
			.scopes("read")
			.authorizedGrantTypes("password","refresh_token")
			.accessTokenValiditySeconds(20)
			.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accesTokenConverter()));
		
		endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {
		JwtAccessTokenConverter accessTokenConverte = new JwtAccessTokenConverter();
		accessTokenConverte.setSigningKey("viniciusCarvalho");
		return accessTokenConverte;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accesTokenConverter());
	}
	
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
