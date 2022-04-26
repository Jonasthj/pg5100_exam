package no.kristiania.pg5100_exam.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.kristiania.pg5100_exam.security.jwt.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.log

class CustomAuthenticationFilter(
    @Autowired private val authManager: AuthenticationManager
    ): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val body = request.reader.lines().collect(Collectors.joining())
        val loginInfo = jacksonObjectMapper().readValue(body, LoginInfo::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken(loginInfo.username, loginInfo.password)
        return authManager.authenticate(authenticationToken)
    }

    override fun setAuthenticationFailureHandler(failureHandler: AuthenticationFailureHandler?) {
        super.setAuthenticationFailureHandler(failureHandler)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        // Give cookie with JWT
        // Principal = person that is successfully authenticated
        val user: User = authResult?.principal as User
        val accessToken = JwtUtil.createToken(user, request?.requestURL.toString())
        response?.contentType = APPLICATION_JSON_VALUE
        response?.addCookie(Cookie("access_token", accessToken))
    }
}

data class LoginInfo (val username: String, val password: String)