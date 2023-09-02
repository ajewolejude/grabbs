//package com.example.grabbs.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	http
//		.csrf().disable()
//		.authorizeRequests().antMatchers("/auth/login").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.formLogin().loginPage("/auth/login").permitAll();
//	}
//
//   @Autowired
//   public void configureGlobal(AuthenticationManagerBuilder auth, HttpServletRequest request) throws Exception {
//      auth
//         .inMemoryAuthentication()
//         .withUser("admin@skote.com").password("1234").roles("USER");
//   }
//
//	 @Bean
//	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	     http.sessionManagement()
//	         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//	     return http.build();
//	 }
//
//   @Bean
//   public static NoOpPasswordEncoder passwordEncoder() {
//	   return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//   }
//
//}