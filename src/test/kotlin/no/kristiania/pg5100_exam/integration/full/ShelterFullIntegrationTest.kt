package no.kristiania.pg5100_exam.integration.full

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShelterFullIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldCreateUserAndLoginAndGetAllAnimals(){
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
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/shelter/all"){
            if (cookie != null) {
                cookie(cookie)
            }
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldCreateUserAndLoginAndGetSingleAnimal(){

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
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/shelter/get?id=5"){
            if (cookie != null) {
                cookie(cookie)
            }
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldCreateUserAndLoginAndAddNewAnimal(){
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
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.post("/api/shelter/add"){
            if (cookie != null) {
                cookie(cookie)
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\": \"ulf\",\n" +
                    "    \"age\": \"35\",\n" +
                    "    \"weight\": \"100\",\n" +
                    "    \"typeId\": \"1\",\n" +
                    "    \"breedId\": \"1\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldCreateUserAndLoginAndUpdateExistingAnimal(){
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
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.put("/api/shelter/update?id=5"){
            if (cookie != null) {
                cookie(cookie)
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\": \"per\",\n" +
                    "    \"age\": \"35\",\n" +
                    "    \"weight\": \"50\",\n" +
                    "    \"typeId\": \"1\",\n" +
                    "    \"breedId\": \"1\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldCreateUserAndLoginAndDisplayAnimalsWithTypeAndBreed(){
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
                    "    \"username\": \"${username}\",\n" +
                    "    \"password\": \"${password}\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/shelter/display"){
            if (cookie != null) {
                cookie(cookie)
            }

        }
            .andExpect { status { is2xxSuccessful() } }
    }
}