package no.kristiania.pg5100_exam.service

import no.kristiania.pg5100_exam.controller.NewUserInfo
import no.kristiania.pg5100_exam.model.AuthorityEntity
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.repository.AuthorityRepository
import no.kristiania.pg5100_exam.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val authorityRepository: AuthorityRepository
) : UserDetailsService {

    fun getAuthorities(): List<AuthorityEntity> {
        return authorityRepository.findAll()
    }

    fun registerUser(newUserInfo: NewUserInfo) : UserEntity {
        val newUser = UserEntity(username = newUserInfo.username, password = BCryptPasswordEncoder().encode(newUserInfo.password))
        authorityRepository.getByAuthorityName("USER")?.let { newUser.authorities.add(it) }
        return userRepository.save(newUser)
    }

    fun getUsers() : List<UserEntity> {
        return userRepository.findAll()
    }

    fun getUserByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepository.findByUsername(it)
            return User(
                user?.username,
                user?.password,
                user?.authorities?.map { authority -> SimpleGrantedAuthority(authority.authorityName) }
            )
        }
        // TODO: Throw specific exception (Do not say anything about user existing)

        throw Exception("Error")
    }
}