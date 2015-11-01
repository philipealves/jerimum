package br.com.jerimum.fw.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public abstract class JerimumDefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String XSRF_TOKEN = "XSRF-TOKEN";
	
	/**
	 * 
	 * @param auth
	 * @throws Exception
	 */
	protected abstract void configureGlobal(AuthenticationManagerBuilder auth) throws Exception;
	
	/**
	 * 
	 * @return {@link String}
	 */
	protected abstract String getLoginPage();

	/**
	 * 
	 * @return String[]
	 */
	protected abstract String[] getUnsecuredResources();

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (ArrayUtils.isEmpty(getUnsecuredResources())) {
			http.httpBasic().and().authorizeRequests().anyRequest().authenticated().and().formLogin()
					.loginPage(getLoginPage()).permitAll().and().logout().permitAll().and().csrf()
					.csrfTokenRepository(csrfTokenRepository()).and()
					.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		} else {
			http.httpBasic().and().authorizeRequests().antMatchers(getUnsecuredResources()).permitAll().anyRequest()
					.authenticated().and().formLogin().loginPage(getLoginPage()).permitAll().and().logout().permitAll()
					.and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
					.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		}
	}

	/**
	 * 
	 * @return {@link Filter}
	 */
	protected Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, XSRF_TOKEN);
					String token = csrf.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie(XSRF_TOKEN, token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	/**
	 * 
	 * @return {@link CsrfTokenRepository}
	 */
	protected CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-" + XSRF_TOKEN);
		return repository;
	}
	
}
