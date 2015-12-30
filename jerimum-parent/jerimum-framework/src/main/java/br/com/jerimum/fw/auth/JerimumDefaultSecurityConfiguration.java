package br.com.jerimum.fw.auth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
	 * Retorna uma string com o endereco relativo da pagina de login.
	 * 
	 * @return {@link String}
	 */
	protected abstract String getLoginPage();

	/**
	 * Retorna uma string com o endereco relativo da pagina de acesso negado.
	 * 
	 * @return {@link String}
	 */
	protected abstract String getAccessDeniedPage();

	/**
	 * Retorna um array com a lista dos recursos que nao necessitam de
	 * autenticacao para que sejam acessados.
	 * 
	 * @return String[]
	 */
	protected abstract String[] getUnsecuredResources();

	/**
	 * Retorna um mapa onde a chave corresponde ao recurso a ser acessado e o
	 * valor corresponde ao array de ROLES que permitem o acesso a esse recurso.
	 * 
	 * @return {@link Map}
	 */
	protected abstract Map<String, String[]> getAuthorities();

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(getUnsecuredResources());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic();

		if (StringUtils.isNoneBlank(getAccessDeniedPage())) {
			http.exceptionHandling().accessDeniedPage(getAccessDeniedPage()).and().authorizeRequests()
					.antMatchers(getAccessDeniedPage()).permitAll();
		}

		if (ArrayUtils.isNotEmpty(getUnsecuredResources())) {
			http.authorizeRequests().antMatchers(getUnsecuredResources()).permitAll();
		}

		Map<String, String[]> authorities = getAuthorities();
		if (MapUtils.isNotEmpty(authorities)) {
			for (String url : authorities.keySet()) {
				http.authorizeRequests().antMatchers(url).hasAnyAuthority(authorities.get(url));
			}
		}

		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage(getLoginPage()).permitAll()
				.and().logout().permitAll().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
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
