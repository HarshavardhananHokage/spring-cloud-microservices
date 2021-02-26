package com.harsh.photo.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationHeaderFilter extends AbstractGatewayFilterFactory<AuthenticationHeaderFilter.Config> {

	@Autowired
	Environment environment;

	public AuthenticationHeaderFilter() {

		super(Config.class);
	}

	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {
		System.out.println("Hello");
		return (exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();

			System.out.println(request.getMethod().toString() + " BODY " + request.getBody().toString());
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);

			String authorizationHeaderValue = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeaderValue.replace("Bearer", "");
			
			if(!isValidJWT(jwt))
				return onError(exchange, "User unauthorized", HttpStatus.UNAUTHORIZED);
			
			return chain.filter(exchange);
		};
	}

	public boolean isValidJWT(String jwt) {

		String user = null;
		System.out.println("Secret: " +environment.getProperty("token.hmac512.secret"));
		try {
			user = Jwts.parser().setSigningKey(environment.getProperty("token.hmac512.secret")).parseClaimsJws(jwt).getBody()
					.getSubject();
			
			System.out.println("User from JWT: " +user);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (user == null || user.isEmpty())
			return false;

		return true;
	}

	public Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {

		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);

		return response.setComplete();
	}

}
