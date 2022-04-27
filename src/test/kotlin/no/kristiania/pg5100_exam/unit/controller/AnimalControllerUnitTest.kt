package no.kristiania.pg5100_exam.unit.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.controller.AnimalController.NewAnimal
import no.kristiania.pg5100_exam.model.AnimalEntity
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
import org.springframework.test.web.servlet.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false) // no security
class AnimalControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun animalService() = mockk<AnimalService>()
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired private lateinit var animalService: AnimalService
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAnimals(){
        every { animalService.getAllAnimals() } answers {
            listOf(AnimalEntity(1, "Tom", 1, 1F, 1, 1))
        }

        mockMvc.get("/api/shelter/all"){

        }

            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldGetASingleAnimal(){
        every { animalService.getSingleAnimalResponse(1) } answers {
            ResponseEntity.ok().body(AnimalEntity(1, "Tom", 1, 1F, 1, 1))
        }

        mockMvc.get("/api/shelter/get?id=1"){
        }
            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldFailToGetASingleAnimalResponse(){
        every { animalService.getSingleAnimalResponse(1) } answers {
            ResponseEntity.notFound().build()
        }

        mockMvc.get("/api/shelter/get?id=1"){
        }
            .andExpect { status { is4xxClientError() } }
    }
    
    @Test
    fun shouldAddNewAnimal(){
        val newAnimal = NewAnimal("Tom", 1, 1F, 1, 1)
        every { animalService.addNewAnimal(newAnimal) } answers {
            AnimalEntity(1, newAnimal.name, newAnimal.age, newAnimal.weight, newAnimal.typeId, newAnimal.breedId)
        }

        mockMvc.post("/api/shelter/add"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\": \"Tom\",\n" +
                    "    \"age\": 1,\n" +
                    "    \"weight\": 1,\n" +
                    "    \"typeId\": 1,\n" +
                    "    \"breedId\": 1\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun shouldDisplayAnimalsWithTypeAndBreed(){
        every { animalService.displayAllAnimalsWithTypeAndBreedName() } answers {
            ResponseEntity.ok().body(listOf())
        }

        mockMvc.get("/api/shelter/display") {

        }

            .andExpect { status { isOk()} }
    }

    @Test
    fun shouldUpdateAnimal(){
        val newAnimal = NewAnimal("Tom", 1, 1F, 1, 1)
        every { animalService.updateAnimalById(1, newAnimal) } answers {
            ResponseEntity.ok().body(AnimalEntity(1, "Tom", 1, 1F, 1, 1))
        }

        mockMvc.put("/api/shelter/update?id=1"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\": \"Tom\",\n" +
                    "    \"age\": 1,\n" +
                    "    \"weight\": 1,\n" +
                    "    \"typeId\": 1,\n" +
                    "    \"breedId\": 1\n" +
                    "}"
        }

            .andExpect { status { isOk() } }
    }

    @Test
    fun shouldDeleteAnimalById(){
        every { animalService.deleteAnimalById(1) } answers {
            ResponseEntity.ok().body("Deletion Successful")
        }

        mockMvc.delete("/api/shelter/delete?id=1"){

        }

            .andExpect { status { isOk() } }
    }
}