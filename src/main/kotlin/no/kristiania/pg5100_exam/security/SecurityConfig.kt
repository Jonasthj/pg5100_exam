package no.kristiania.pg5100_exam.security

import no.kristiania.pg5100_exam.security.filter.CustomAuthenticationFilter
import no.kristiania.pg5100_exam.security.filter.CustomAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userDetailsService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
    ) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
        // setting default login to /api/login
        authenticationFilter.setFilterProcessesUrl("/api/authentication")
        http.csrf().disable()
        http.sessionManagement().disable() // All future requests has jwt cookie
        http.authorizeRequests() // LEAST RESTRICTIVE TO MOST RESTRICTIVE
            .antMatchers("/api/authentication").permitAll()
            .antMatchers("/api/user/register").permitAll()
            .antMatchers("/api/shelter/all").hasAuthority("USER")
            .antMatchers("/api/shelter/update").hasAuthority("USER") // param
            .antMatchers("/api/shelter/get").hasAuthority("USER") // param
            .antMatchers("/api/shelter/add").hasAuthority("ADMIN")
            .antMatchers("/api/shelter/delete").hasAuthority("ADMIN") // param
            .antMatchers("/api/user/all").hasAuthority("ADMIN")
            .antMatchers("/api/user/authorities").hasAuthority("ADMIN")
        // Authenticate every single request that comes through, even if permitAll()
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(authenticationFilter)
        // Adding authorization before authentication, so that new users doesnt auto fail authentication
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}