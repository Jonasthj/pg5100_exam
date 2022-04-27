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
@RequestMapping("/api/user")
class AuthController(@Autowired private val userService: UserService) {

    // All permitted
    @PostMapping("/register")
    fun registerUser(@RequestBody newUserInfo: NewUserInfo) : ResponseEntity<UserEntity> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString())
        return ResponseEntity.created(uri).body(userService.registerUser(newUserInfo))
    }

    // Admin endpoints
    @GetMapping("/all")
    fun getAllUsers() : ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getAllUsers())
    }

    @DeleteMapping("/delete")
    fun deleteUserById(@RequestParam id: Long) : ResponseEntity<String> {
        return userService.deleteUserById(id)
    }

    @GetMapping("/authorities")
    fun getAuthorities() : ResponseEntity<List<AuthorityEntity>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }
}

data class NewUserInfo(
    val username: String? = null,
    val password: String? = null
)