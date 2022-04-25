package no.kristiania.pg5100_exam.integration

import no.kristiania.pg5100_exam.controller.NewUserInfo
import no.kristiania.pg5100_exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(UserService::class)
class DBIntegrationTest(
    @Autowired private val userService: UserService
) {

    @Test
    fun shouldGetUsers(){
        val result = userService.getUsers()
        assert(result.size == 1)
    }

    @Test
    fun registerAndFindUser(){
        val newUser = NewUserInfo("petter", "pan")
        val createdUser = userService.registerUser(newUser)
        assert(createdUser.username.equals("petter"))
        val foundUser = userService.loadUserByUsername("petter")
        assert(createdUser.username == foundUser.username)
    }

    @Test
    fun createUserAndGetByUsername(){
        val newUser = NewUserInfo("petter", "pan")
        val createdUser = userService.registerUser(newUser)
        assert(createdUser.username == "petter")
        val foundUser = userService.getUserByUsername("petter")
        assert(foundUser?.username == "petter")
    }
}