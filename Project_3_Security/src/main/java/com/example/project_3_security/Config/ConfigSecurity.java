package com.example.project_3_security.Config;
import com.example.project_3_security.Model.User;
import com.example.project_3_security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigSecurity {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findUserByUsername(username);
            if (user == null) throw new UsernameNotFoundException("wrong username or password");
            return user;
        };
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService());
        dao.setPasswordEncoder(new BCryptPasswordEncoder());
        return dao;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/customer/add").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/update").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/delete").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/get").hasAuthority("CUSTOMER")

                .requestMatchers("/api/v1/employee/add/{userId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/update/{employeeId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/delete/{employeeId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/get/{employeeId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/get-by-user/{userId}").hasAuthority("ADMIN")

                .requestMatchers("/api/v1/accounts").hasAuthority("CUSTOMER")                                   // إنشاء حساب (POST)
                .requestMatchers("/api/v1/accounts/{id}/activate").hasAuthority("ADMIN")                        // تفعيل
                .requestMatchers("/api/v1/accounts/{id}/block").hasAuthority("ADMIN")                           // حظر
                .requestMatchers("/api/v1/accounts/{id}").hasAuthority("CUSTOMER")                              // تفاصيل حسابي
                .requestMatchers("/api/v1/accounts/customer/{customerId}").hasAuthority("ADMIN")                // حسابات عميل معيّن
                .requestMatchers("/api/v1/accounts/{id}/deposit").hasAuthority("CUSTOMER")                      // إيداع (amount كـ RequestParam أو PathVar)
                .requestMatchers("/api/v1/accounts/{id}/withdraw").hasAuthority("CUSTOMER")                     // سحب
                .requestMatchers("/api/v1/accounts/transfer").hasAuthority("CUSTOMER")                          // تحويل (RequestParam)

                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return http.build();
    }
}

