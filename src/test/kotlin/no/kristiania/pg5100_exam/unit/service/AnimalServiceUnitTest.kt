package no.kristiania.pg5100_exam.unit.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.model.AnimalEntity
import no.kristiania.pg5100_exam.repository.AnimalRepository
import no.kristiania.pg5100_exam.service.AnimalService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestTemplate

class AnimalServiceUnitTest {

    private val animalRepository = mockk<AnimalRepository>()
    private val animalService = AnimalService(animalRepository)

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
    fun shouldGetAnimalByName(){
        every { animalRepository.getByName(any()) } answers {
            AnimalEntity(null, firstArg(), null, null, null, null)
        }

        val animal = animalService.getByName("Petter")

        assert(animal != null)
        assert(animal?.name == "Petter")
    }

    @Test
    fun shouldAddNewAnimal(){

    }

    @Test
    fun shouldDeleteAnimalById() {
        // or name?
    }

    @Test
    fun shouldUpdateAnimalById(){
        // or name?
    }

}