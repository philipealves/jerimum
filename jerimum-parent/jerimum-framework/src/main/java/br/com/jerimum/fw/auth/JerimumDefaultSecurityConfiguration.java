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
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * Default security configuration based on Spring Security.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public abstract class JerimumDefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String XSRF_TOKEN = "XSRF-TOKEN";

    protected abstract void configureGlobal(AuthenticationManagerBuilder auth) throws Exception;
    
    protected abstract PasswordEncoder getPasswordEncoder();

    /**
     * Retorna uma string com o endereco relativo da pagina de login.
     * 
     * @return {@link String}
     */
    protected abstract String getLoginPage();
    
    /**
     * Retorna uma string com o endereco relativo da pagina de login.
     * 
     * @return {@link String}
     */
    protected abstract String getDefaultLoggedPage();

    /**
     * Retorna uma string com o endereco relativo da pagina de acesso negado.
     * 
     * @return {@link String}
     */
    protected abstract String getAccessDeniedPage();

    /**
     * Retorna um array com a lista dos recursos que nao necessitam de autenticacao para que sejam
     * acessados.
     * 
     * @return String[]
     */
    protected abstract String[] getUnsecuredResources();

    /**
     * Retorna um mapa onde a chave corresponde ao recurso a ser acessado e o valor corresponde ao
     * array de ROLES que permitem o acesso a esse recurso.
     * 
     * @return {@link Map}
     */
    protected abstract Map<String, String[]> getAuthorities();

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (StringUtils.isNoneBlank(getAccessDeniedPage())) {
            http
                .exceptionHandling().accessDeniedPage(getAccessDeniedPage())
                .and().authorizeRequests().antMatchers(getAccessDeniedPage()).permitAll();
        }
        Map<String, String[]> authorities = getAuthorities();
        if (MapUtils.isNotEmpty(authorities)) {
            for (String url : authorities.keySet()) {
                http.authorizeRequests().antMatchers(url).hasAnyAuthority(authorities.get(url));
            }
        }
        
        http
            .authorizeRequests()
                .antMatchers(getUnsecuredResources()).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl(getDefaultLoggedPage())
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(ajaxAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .loginPage(getLoginPage())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl(getLoginPage())
                .permitAll()
                .and()
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
            .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

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

    protected CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-" + XSRF_TOKEN);
        return repository;
    }
    
    protected AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
                if (StringUtils.equalsIgnoreCase(Boolean.TRUE.toString(), request.getHeader("X-Login-Ajax-call"))) {
                    response.getWriter().print("ok");
                    response.getWriter().flush();
                }
                else {
                    new SavedRequestAwareAuthenticationSuccessHandler().onAuthenticationSuccess(request, response, authentication);
                }
            }
        };
    }
    
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("ISO-8859-1");
                response.getOutputStream().println(String.format("{ \"error\": \"%s\" }", exception.getMessage()));             
            }
        };
    }

}
