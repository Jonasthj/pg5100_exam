package no.kristiania.pg5100_exam.unit.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.controller.AnimalController.NewAnimal
import no.kristiania.pg5100_exam.model.AnimalEntity
import no.kristiania.pg5100_exam.repository.AnimalRepository
import no.kristiania.pg5100_exam.service.AnimalService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.persistence.EntityNotFoundException

class AnimalServiceUnitTest {

    private val animalRepository = mockk<AnimalRepository>()
    private val animalService = AnimalService(animalRepository)

    @Test
    fun shouldGetAnimalById(){
        val testAnimal1 = AnimalEntity(1, "Petter", null, null, null, null)

        every { testAnimal1.id?.let { animalRepository.getById(it) } } answers {
            testAnimal1
        }

        val animal = animalService.getById(1)
        assert(animal?.name == "Petter")
    }

    @Test
    fun shouldGetAllAnimals(){
        val testAnimal1 = AnimalEntity(null, "Petter", null, null, null, null)
        val testAnimal2 = AnimalEntity(null, "Kjartan", null, null, null, null)

        every { animalRepository.findAll() } answers {
            mutableListOf(testAnimal1, testAnimal2)
        }

        val animals = animalService.getAllAnimals()

        assert(animals.size == 2)
        assert(animals[0]?.name == "Petter")
        assert(animals[1]?.name == "Kjartan")
    }



    @Test
    fun shouldAddNewAnimal(){
        val newAnimal = NewAnimal("Petter", null, null, null, null)
        val animal = AnimalEntity(1, "Petter", null, null, null, null)

        every { animalRepository.save(any()) } answers {
            firstArg()
        }

        val addedAnimal = animalService.addNewAnimal(newAnimal)

        assert(addedAnimal?.name == "Petter")
    }

    @Test
    fun shouldUpdateAnimalById(){
        val newAnimal = NewAnimal("Petter", null, null, null, null)
        val animal = AnimalEntity(1, "Petter" ,null, null, null, null)

        every { animalRepository.save(any()) } answers {
            firstArg()
        }

        every { animalRepository.getById(1) } answers {
            animal
        }
        animalRepository.save(animal)

        val response: ResponseEntity<AnimalEntity>? = animalService.updateAnimalById(1, newAnimal)
        assertEquals("Petter", response?.body?.name)
        assertEquals(HttpStatus.OK, response?.statusCode)
    }

    @Test
    fun shouldFailToUpdateAnimalByMissingId(){
        val newAnimal = NewAnimal("Petter", null, null, null, null)

        every { animalRepository.save(any()) } answers {
            firstArg()
        }

        val response: ResponseEntity<AnimalEntity>? = animalService.updateAnimalById(1, newAnimal)

        assertEquals(HttpStatus.NOT_FOUND, response?.statusCode)
    }

    @Test
    fun shouldGetSingleAnimalResponse() {
        val animal = AnimalEntity(1, "Petter" ,null, null, null, null)

        every { animalRepository.getById(1) } answers {
            animal
        }

        val retrievedAnimalResponse = animalService.getSingleAnimalResponse(1)

        assertEquals(HttpStatus.OK, retrievedAnimalResponse?.statusCode)
        assertEquals("Petter", retrievedAnimalResponse?.body?.name)
    }

    @Test
    fun shouldFailToGetSingleAnimalResponse() {
        every { animalRepository.getById(1) }
            .throws(EntityNotFoundException())

        val retrievedAnimalResponse = animalService.getSingleAnimalResponse(1)

        assert(retrievedAnimalResponse.toString().contains(HttpStatus.NOT_FOUND.toString()))
    }

    @Test
    fun shouldDeleteAnimalById(){
        every { animalRepository.deleteById(1) } answers {
            nothing
        }
        val responseString = animalService.deleteAnimalById(1)

        assertEquals(HttpStatus.OK, responseString.statusCode)
    }

    @Test
    fun shouldFailToDeleteById(){
        every { animalRepository.deleteById(1) }
            .throws(EmptyResultDataAccessException(1))

        val responseString = animalService.deleteAnimalById(1)

        assert(responseString.toString().contains(HttpStatus.NOT_FOUND.toString()))
    }
}