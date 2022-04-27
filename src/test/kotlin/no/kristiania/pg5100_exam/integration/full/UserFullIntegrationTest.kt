package no.kristiania.pg5100_exam.integration.full

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserFullIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldRegisterNewUserAndLogIn(){
        val randomNum = (0..1000).random()
        val username = "user$randomNum"
        val password = "pass$randomNum"
        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }

        mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldGetAllUsers(){
        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
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
    }

    @Test
    fun shouldCreateUserAndDeleteById(){
        val randomNum = (0..1000).random()
        val username = "user$randomNum"
        val password = "pass$randomNum"
        mockMvc.post("/api/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }

        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.delete("/api/user/delete?id=2"){
            if (cookie != null) {
                cookie(cookie)
            }
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldGetAllAuthorities() {
        val loggedInUser = mockMvc.post("/api/authentication") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"username\": \"admin\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/user/authorities"){
            if (cookie != null) {
                cookie(cookie)
            }
        }
            .andExpect { status { is2xxSuccessful() } }
    }
}