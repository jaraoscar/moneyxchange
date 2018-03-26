package com.belatrixsf.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Used to define the HTTP requests configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JsonWebTokenAuthEntryPoint jsonWebTokenAuthEntryPoint;
	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.getEncoder());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/token/*", "/signup").permitAll().anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jsonWebTokenAuthEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(this.getAuthFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * The CORS filtering for the HTTP requests.
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	
    	configuration.setAllowedOrigins(Arrays.asList("*"));
    	configuration.setAllowCredentials(true);
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    	configuration.setMaxAge(Long.valueOf(3600));
    	configuration.setAllowedHeaders(Arrays.asList("X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers"));
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	
    	return source;
    }

    /**
     * Generate a new instance of a JsonWebTokenAuthFilter
     * @return JsonWebTokenAuthFilter
     * @throws Exception
     */
    @Bean
    public JsonWebTokenAuthFilter getAuthFilterBean() throws Exception {
        return new JsonWebTokenAuthFilter();
    }

    /**
     * Generate a new instance of a BCryptPasswordEncoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
