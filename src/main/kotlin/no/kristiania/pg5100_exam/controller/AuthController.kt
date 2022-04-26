package no.kristiania.pg5100_exam.controller

import no.kristiania.pg5100_exam.model.AuthorityEntity
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/api")
class AuthController(@Autowired private val userService: UserService) {

    // Login is "/api/user/login"

    // All permitted

    @PostMapping("/authentication")
    fun registerUser(@RequestBody newUserInfo: NewUserInfo) : ResponseEntity<UserEntity> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString())
        return ResponseEntity.created(uri).body(userService.registerUser(newUserInfo))
    }

    // Admin endpoints

    @GetMapping("/admin/user/all")
    fun getAllUsers() : ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getAllUsers())
    }

    @GetMapping("/admin/authority/all")
    fun getAuthorities() : ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    @DeleteMapping("/user/delete")
    fun deleteUserById(@RequestParam id: Long) : ResponseEntity<String> {
        return userService.deleteUserById(id)
    }


}

data class NewUserInfo(
    val username: String? = null,
    val password: String? = null
)