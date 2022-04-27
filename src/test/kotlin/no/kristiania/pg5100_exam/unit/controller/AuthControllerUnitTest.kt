package no.kristiania.pg5100_exam.unit.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.model.AuthorityEntity
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.service.AnimalService
import no.kristiania.pg5100_exam.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime


@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false) // no security when testing here
class AuthControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
        @Bean
        fun animalService() = mockk<AnimalService>()
    }

    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun shouldRegisterUser() {
        val randomNum = (0..1000).random()
        val username = "user$randomNum"
        val password = "pass$randomNum"


        every { userService.registerUser(any()) } answers {
            UserEntity(
                1,
                username,
                password,
                LocalDateTime.now(),
                true,
                mutableListOf(AuthorityEntity(1, "USER"))
            )
        }

        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
            content = "{\n" +
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}}\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldGetAllUsers(){
        val testUser = UserEntity(username = "petter", password = "pan", authorities = mutableListOf(AuthorityEntity(1, "ADMIN")))

        every { userService.getAllUsers() } answers {
            mutableListOf(testUser)
        }

        mockMvc.get("/api/user/all"){

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    @Test
    fun shouldGetAuthorities(){

        every { userService.getAuthorities() } answers {
            listOf(AuthorityEntity(authorityName = "ADMIN"),
                AuthorityEntity(authorityName = "USER"))
        }

        mockMvc.get("/api/user/authorities") {

        }
            .andExpect { status { isOk() } }
            .andReturn().response.contentAsString.contains("USER")
    }

    @Test
    fun shouldDeleteUserById() {

        every { userService.deleteUserById(1) } answers {
            ResponseEntity.ok().body("Deletion was successful")
        }

        mockMvc.delete("/api/user/delete?id=1") {

        }
            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldFailToDeleteById() {

        every { userService.deleteUserById(1) } answers {
            ResponseEntity.notFound().build()
        }

        mockMvc.delete("/api/user/delete?id=1") {

        }
            .andExpect { status { isNotFound() } }
    }
}