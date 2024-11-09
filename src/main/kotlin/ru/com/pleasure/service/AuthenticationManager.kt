package ru.com.pleasure.service

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(private val jwtUtil: JWTUtil) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()
        val username = try {
            jwtUtil.getUsernameFromToken(authToken)
        } catch (e: Throwable) {
            null
        }

        return if (username != null && jwtUtil.validateToken(authToken)) {
            val claims = jwtUtil.getAllClaimsFromToken(authToken)
            val authorities = (claims["authorities"] as? List<*>)?.mapNotNull { it as? String }?.map(::SimpleGrantedAuthority) ?: emptyList()

            Mono.just(UsernamePasswordAuthenticationToken(username, null, authorities))
        } else {
            Mono.empty()
        }
    }
}