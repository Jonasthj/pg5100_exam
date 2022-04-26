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

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepository>()
    private val authorityRepo = mockk<AuthorityRepository>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun shouldGetUsers(){
        val testUser1 = UserEntity(username = "petter", password = "pan")
        val testUser2 = UserEntity(username = "kjartan", password = "secret")
        every { userRepo.findAll() } answers {
            mutableListOf(testUser1, testUser2)
        }

        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users[0].username == "petter" && users[0].password == "pan")
        assert(users[1].username == "kjartan" && users[1].password == "secret")
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
}