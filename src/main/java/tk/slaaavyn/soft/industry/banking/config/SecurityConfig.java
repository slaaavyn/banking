package tk.slaaavyn.soft.industry.banking.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtConfigurer;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /* AUTH */
                .antMatchers(EndpointConstants.AUTH_ENDPOINT + "/update-password/**").authenticated()
                .antMatchers(EndpointConstants.AUTH_ENDPOINT + "/**").permitAll()

                /* USER */
                .antMatchers(EndpointConstants.USER_ENDPOINT + "/customer-info").hasRole("USER")
                .antMatchers(EndpointConstants.USER_ENDPOINT + "/**").hasRole("ADMIN")

                /* BALANCE */
                .antMatchers(HttpMethod.GET,EndpointConstants.BALANCE_ENDPOINT + "/**").authenticated()
                .antMatchers(EndpointConstants.BALANCE_ENDPOINT + "/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}