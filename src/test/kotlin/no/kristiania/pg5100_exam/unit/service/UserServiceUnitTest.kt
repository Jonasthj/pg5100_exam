package no.kristiania.pg5100_exam.unit.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.controller.NewUserInfo
import no.kristiania.pg5100_exam.model.AuthorityEntity
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.repository.AuthorityRepository
import no.kristiania.pg5100_exam.repository.UserRepository
import no.kristiania.pg5100_exam.service.UserService
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.dao.EmptyResultDataAccessException

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepository>()
    private val authorityRepo = mockk<AuthorityRepository>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun shouldGetAllUsers(){
        val testUser1 = UserEntity(username = "petter", password = "pan")
        val testUser2 = UserEntity(username = "kjartan", password = "secret")
        every { userRepo.findAll() } answers {
            mutableListOf(testUser1, testUser2)
        }

        val users = userService.getAllUsers()
        assert(users.size == 2)
        assert(users[0].username == "petter" && users[0].password == "pan")
        assert(users[1].username == "kjartan" && users[1].password == "secret")
    }

    @Test
    fun shouldGetUserByUsername(){
        every { userRepo.findByUsername("Petter") } answers {
            UserEntity(null, "Petter", null, null, null, mutableListOf())
        }

        val retrievedUser = userService.getUserByUsername("Petter")

        assertEquals("Petter", retrievedUser?.username)
    }

    @Test
    fun shouldRegisterNewUser(){
        every { userRepo.save(any()) } answers {
            firstArg() // returns the first argument that was passed in
        }

        every { authorityRepo.getByAuthorityName(any()) } answers {
            AuthorityEntity(authorityName = "USER")
        }

        val createdUser = userService.registerUser(NewUserInfo("petter", "pan"))
        assertEquals("petter", createdUser.username)
        assertEquals(true, createdUser.enabled)
    }

    @Test
    fun shouldDeleteUserById(){
        every { userRepo.deleteById(1) } answers {
            nothing
        }

        val responseString = userService.deleteUserById(1)
        assert(responseString.statusCodeValue == 200)
    }

    @Test
    fun shouldFailToDeleteUserById(){
        every { userRepo.deleteById(1) }
            .throws(EmptyResultDataAccessException(1))

        val responseString = userService.deleteUserById(1)

        assert(responseString.statusCodeValue == 404)
    }
}