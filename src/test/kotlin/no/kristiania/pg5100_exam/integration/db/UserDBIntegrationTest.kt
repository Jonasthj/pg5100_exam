package no.kristiania.pg5100_exam.integration.db

import no.kristiania.pg5100_exam.controller.NewUserInfo
import no.kristiania.pg5100_exam.service.AnimalService
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
class UserDBIntegrationTest(
    @Autowired private val userService: UserService
) {

    @Test
    fun shouldGetAllUsers(){
        val result = userService.getAllUsers()
        assert(result.size == 1)
    }

    @Test
    fun shouldRegisterAndFindUser(){
        val newUser = NewUserInfo("petter", "pan")
        val createdUser = userService.registerUser(newUser)
        assert(createdUser.username.equals("petter"))
        val foundUser = userService.loadUserByUsername("petter")
        assert(createdUser.username == foundUser.username)
    }

    @Test
    fun shouldCreateUserAndGetByUsername(){
        val newUser = NewUserInfo("petter", "pan")
        val createdUser = userService.registerUser(newUser)
        assert(createdUser.username == "petter")
        val foundUser = userService.getUserByUsername("petter")
        assert(foundUser?.username == "petter")
    }

    @Test
    fun shouldDeleteUserById(){
        val newUser = NewUserInfo("petter", "pan")
        val createdUser = userService.registerUser(newUser)
        val deleteResponse = userService.deleteUserById(createdUser.id!!)
        assert(deleteResponse.statusCode.is2xxSuccessful)
    }

    @Test
    fun shouldFailToDeleteUserById() {
        val deleteResponse = userService.deleteUserById(123123123L)
        assert(deleteResponse.statusCode.is4xxClientError)
    }
}