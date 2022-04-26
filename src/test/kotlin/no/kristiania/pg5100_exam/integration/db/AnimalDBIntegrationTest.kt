package no.kristiania.pg5100_exam.integration.db

import no.kristiania.pg5100_exam.controller.AnimalController.NewAnimal
import no.kristiania.pg5100_exam.service.AnimalService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AnimalService::class)
class AnimalDBIntegrationTest(@Autowired private val animalService: AnimalService) {

    @Test
    fun shouldGetAllAnimals(){
        val result = animalService.getAllAnimals()
        assert(result.size == 18)
    }

    @Test
    fun shouldAddAndRetrieveAnimalById(){
        val newAnimal = NewAnimal(
            name = "PETTER"
        )

        animalService.addNewAnimal(newAnimal)
        assert(animalService.getById(19)?.name == "PETTER")
    }
}