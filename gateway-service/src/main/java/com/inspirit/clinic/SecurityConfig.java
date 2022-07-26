//package com.inspirit.clinic;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import static java.lang.String.format;
//
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    public SecurityConfig() {
//        super();
//
//
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http = http.cors().and().csrf().disable()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(
////                        (request, response, ex) -> {
////                            System.out.println("Unauthorized request - "+ ex.getMessage());
////                        }
////                ).and();
//
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//
//                .anyRequest().permitAll();
//
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
////        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//}
//
