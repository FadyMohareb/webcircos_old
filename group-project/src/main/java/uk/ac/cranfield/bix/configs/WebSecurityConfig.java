package uk.ac.cranfield.bix.configs;

import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import uk.ac.cranfield.bix.utilities.BixPasswordEncoder;

/**
 * Spring Boot security configuration. Web service uses the http protocol. 
 * @author vmuser
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Declare all the pages end-user can access with or without permission. 
     * Login and logout are automatically handle with spring, just need to declare redirection.
     * @param http communication protocol
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    //.antMatchers(HttpMethod.POST,"/comments.json").permitAll()
                    .antMatchers("/resources/**","/recognizeFileName","/recognizeFileType", "/registrationReact", "/loginReact", "/import/getAll","/loginReactAction", "/import/copyFile", "/manageAccount", "/home", "/controller/remove", "/controller/upload", "/refresh", "/circos.data").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/home")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5120MB");
        factory.setMaxRequestSize("5120MB");
        return factory.createMultipartConfig();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(BixPasswordEncoder.getInstance());
    }
    
}
