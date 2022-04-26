package no.kristiania.pg5100_exam.service

import no.kristiania.pg5100_exam.controller.NewUserInfo
import no.kristiania.pg5100_exam.model.AuthorityEntity
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.repository.AuthorityRepository
import no.kristiania.pg5100_exam.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val authorityRepository: AuthorityRepository
) : UserDetailsService {

    fun getAllUsers() : List<UserEntity> {
        return userRepository.findAll()
    }

    fun getUserByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }

    fun registerUser(newUserInfo: NewUserInfo) : UserEntity {
        val newUser = UserEntity(username = newUserInfo.username, password = BCryptPasswordEncoder().encode(newUserInfo.password))
        authorityRepository.getByAuthorityName("USER")?.let { newUser.authorities.add(it) }
        return userRepository.save(newUser)
    }

    fun deleteUserById(id: Long) : ResponseEntity<String> {
        return try {
            userRepository.deleteById(id)
            ResponseEntity.ok().body("Deletion was successful")
        } catch (e1: EmptyResultDataAccessException) {
            ResponseEntity.notFound().build()
        }
    }

    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepository.findAll()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        println("Loading user by username")
        username?.let {
            val user = userRepository.findByUsername(it)
            println("Giving user authorities: " + user?.authorities?.map { SimpleGrantedAuthority(it.authorityName) })
            return User(
                user?.username,
                user?.password,
                user?.authorities?.map { authority -> SimpleGrantedAuthority(authority.authorityName) }
            )
        }
        // TODO: Throw specific exception (Do not say anything about user existing)

        throw UsernameNotFoundException("error authenticating user")
    }


}