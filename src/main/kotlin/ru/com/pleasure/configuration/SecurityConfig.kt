package ru.com.pleasure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import ru.com.pleasure.service.AuthenticationManager
import ru.com.pleasure.service.SecurityContextRepository

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange {
                it.pathMatchers("/api/users/register", "/api/users/login").permitAll()
                    .pathMatchers("/v3/api-docs/**", "/webjars/swagger-ui/**").permitAll()
                    .anyExchange().authenticated()
            }
            .httpBasic {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .build()
    }
}