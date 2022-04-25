package no.kristiania.pg5100_exam.unit

import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.model.UserEntity
import no.kristiania.pg5100_exam.repository.AuthorityRepository
import no.kristiania.pg5100_exam.repository.UserRepository
import no.kristiania.pg5100_exam.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false) // no security when testing here
class AuthControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsers(){
        val testUser = UserEntity(username = "petter", password = "pan")

        every { userService.getUsers() } answers {
            mutableListOf(testUser)
        }

        mockMvc.get("/api/user/all"){

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }
}