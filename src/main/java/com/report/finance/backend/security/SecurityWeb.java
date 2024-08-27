package com.report.finance.backend.security;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@Configuration
@SuppressWarnings("deprecation")
public class SecurityWeb extends WebSecurityConfigurerAdapter{

	@Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
	private UserDetailsService userDetailsService;


	@Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable().cors().and().logout().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
				.antMatchers(HttpMethod.GET, "/generate-error").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/swagger-report-finance/**").permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger*/**", "/swagger-report-finance/**").permitAll()
                .anyRequest()
				.authenticated();
        
    } 


    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("inside configure(AuthenticationManagerBuilder auth)...");

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    

    @Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // Izinkan CORS untuk semua endpoint
                .allowedOrigins("*")  // Izinkan semua domain
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Metode HTTP yang diizinkan
                .allowedHeaders("*")  // Header yang diizinkan
                .allowCredentials(false); 
			}
		};
	}

	@Bean(name = "restTemplateByPassSSL")
	public RestTemplate restTemplateByPassSSL()
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		HostnameVerifier hostnameVerifier = (s, sslSession) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);

	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
