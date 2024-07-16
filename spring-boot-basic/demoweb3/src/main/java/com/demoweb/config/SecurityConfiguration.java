package com.demoweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    // 인증을 제어하는 도구
    public SecurityFilterChain sercurityFilterChain(HttpSecurity http) throws Exception{

        // 인증 설정
        // 1-1
//        http
//                // csrf를 사용하지 않겠다는 설정
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/", "/home").permitAll()
//                        .requestMatchers("/account/**").permitAll() // "/account/**", "/board/**" 여러 개 가능
//                        .requestMatchers("/image/**", "/styles/**").permitAll()
//                        .anyRequest().authenticated())
//                        .httpBasic(AbstractHttpConfigurer::disable)
//                        .formLogin(formLogin -> formLogin
//                                .loginPage("/account/login"));

        // 1-2
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().permitAll())
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/account/login"));
         // 2
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(AbstractHttpConfigurer::disable); // 일반적으로 사용하는 id, pw 입력받아서 로그인하는 행동
//
//        return http.build();

        // 3
//        http
//                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
//                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()); // 일반적으로 사용하는 id, pw 입력받아서 로그인하는 행동
//
//        return http.build();

        // 4
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 미사용 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/board/*write*", "/board/*edit*", "/board/*delete*").authenticated() // authenticated? 로그인한 사용자만 허용 설정
                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole? 권한을 기반으로해서 허용
                        .anyRequest().permitAll()) // 그 외 모든 요청은 인증을 필요설정  authorize? 권한체크 정보.
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/account/login")
                        .usernameParameter("memberId")
                        .passwordParameter("passwd")
                        .loginProcessingUrl("/account/process-login")); // 일반적으로 사용하는 id, pw 입력받아서 로그인하는 행동

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){ // Spring Security가 기본적으로 사용하는 passwordEncoder
        return new BCryptPasswordEncoder();
    }

//    // 1.
//    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println(passwordEncoder().encode("Pa$$w0rd"));
//        InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager(); // 사용자 정보를 메모리에 두겠다
//        userDetailService.createUser(User // 사용자 등록
//                .withUsername("inmemoryuser")
////                .password("{noop}Pa$$w0rd") // Security는 기본적으로 암호화 처리가 되어있음. 따라서 암호화 되지 않은 경우 {noop} 를 붙여줘야 사용 가능함.
//                .password(passwordEncoder() .encode("Pa$$w0rd"))
//                .roles("USER") // ROLE_USER
//                .build());
//        userDetailService.createUser(User // 사용자 등록
//                .withUsername("inmemoryadmin")
//               // .password("{noop}Pa$$w0rd") // Security는 기본적으로 암호화 처리가 되어있음. 따라서 암호화 되지 않은 경우 {noop} 를 붙여줘야 사용 가능함.
//                .password(passwordEncoder() .encode("Pa$$w0rd"))
//                .roles("ADMIN") // ROLE_ADMIN
//                .build());
//        return userDetailService;
//    }

    // 2-1.
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) { // JdbcUserDetailsManager? DB에 저장함
//        return new JdbcUserDetailsManager(dataSource); // 미리 정해진 테이블, SQL을 사용해서 인증처리
//
//    }

    // 2-2.
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) { // JdbcUserDetailsManager? DB에 저장함

        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource); // 미리 정해진 테이블, SQL을 사용해서 인증처리

        // 사용자 정의 테이블을 사용하기 위해 로그인, 권한 검사에 사용할 Query 지정 (약속된 테이블이 아니라 커스텀이라 코드+쿼리문으로 알려 줘야함)
        userDetailsService.setUsersByUsernameQuery("select email,password,enabled "
                + "from custom_users "
                + "where email = ?");
        userDetailsService.setAuthoritiesByUsernameQuery("select email, authority " +
                "from custom_authorities " +
                "where email = ?");
        return userDetailsService;
    }

}
