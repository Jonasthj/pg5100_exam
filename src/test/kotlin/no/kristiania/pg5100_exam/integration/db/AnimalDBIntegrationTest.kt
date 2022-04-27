package no.kristiania.pg5100_exam.integration.db

import no.kristiania.pg5100_exam.controller.AnimalController.NewAnimal
import no.kristiania.pg5100_exam.service.AnimalService
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(AnimalService::class)
class AnimalDBIntegrationTest(@Autowired private val animalService: AnimalService) {

    @Test
    fun shouldAddOneAndGetAllAnimals(){
        val newAnimal = NewAnimal(name = "KARI")
        animalService.addNewAnimal(newAnimal)
        val result = animalService.getAllAnimals()
        assert(result[result.size - 1]?.name.equals("KARI"))
    }

    @Test
    fun shouldAddAndRetrieveAnimalById(){
        val newAnimal = NewAnimal(name = "KARI")
        val saved = animalService.addNewAnimal(newAnimal)
        assert(saved?.id?.let { animalService.getById(it)?.name } == "KARI")
    }

    @Test
    fun shouldSaveAndUpdateAnimalById(){
        val tiger = NewAnimal(
            "TIGER",
            15,
            15F,
            1,
            1
        )

        val savedTiger = animalService.addNewAnimal(tiger)

        val beforeUpdate = NewAnimal(
            "LARS",
            4,
            4F,
            1,
            1
        )

        val afterUpdate = savedTiger?.id?.let { animalService.updateAnimalById(it, beforeUpdate) }

        assertEquals(savedTiger?.id, afterUpdate?.body?.id)
    }

    @Test
    fun shouldFailToUpdateNonExistingAnimalById(){
        val beforeUpdate = NewAnimal(
            "LARS",
            4,
            4F,
            1,
            1
        )
        val afterUpdate = animalService.updateAnimalById(123321L, beforeUpdate)
        assertEquals(HttpStatus.NOT_FOUND, afterUpdate?.statusCode)
    }

    @Test
    fun shouldDeleteAnimalById(){
        val tiger = NewAnimal(
            "tiger",
            4,
            4F,
            1,
            1
        )

        val savedTiger = animalService.addNewAnimal(tiger)

        val status = savedTiger?.id?.let { animalService.deleteAnimalById(it) }

        assert(status?.statusCode?.is2xxSuccessful == true)
    }

    @Test
    fun shouldFailToDeleteAnimalById(){
        val status = animalService.deleteAnimalById(123123123L)
        assert(status.statusCode.is4xxClientError)
    }
}