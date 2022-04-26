package no.kristiania.pg5100_exam.integration.full

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAuthorities(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/user/all"){
            if (cookie != null) {
                cookie(cookie)
            }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andExpect { jsonPath("$") {isArray()} }

    }
}