package no.kristiania.pg5100_exam.service

import no.kristiania.pg5100_exam.controller.AnimalController.NewAnimal
import no.kristiania.pg5100_exam.model.AnimalEntity
import no.kristiania.pg5100_exam.repository.AnimalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class AnimalService(@Autowired private val animalRepository: AnimalRepository) {

    fun getById(id: Long): AnimalEntity? {
        return animalRepository.getById(id)
    }

    fun getAllAnimals(): List<AnimalEntity?> {
        return animalRepository.findAll()
    }

    fun addNewAnimal(newAnimal: NewAnimal): AnimalEntity? {
        val animal = AnimalEntity(
            name = newAnimal.name,
            age = newAnimal.age,
            weight = newAnimal.weight,
            typeId = newAnimal.typeId,
            breedId = newAnimal.breedId
        )
        return animalRepository.save(animal)
    }

    fun updateAnimalById(id: Long, updatedAnimal: NewAnimal?): ResponseEntity<AnimalEntity>? {
        try {
            if (getById(id) != null) {
                val savedAnimal = animalRepository.save(
                    AnimalEntity(
                        id,
                        updatedAnimal?.name,
                        updatedAnimal?.age,
                        updatedAnimal?.weight,
                        updatedAnimal?.typeId,
                        updatedAnimal?.breedId
                        )
                )
                return ResponseEntity.ok().body(savedAnimal)
            }
        } catch (e: Exception) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.notFound().build()
    }


    fun getSingleAnimalResponse(id: Long): ResponseEntity<AnimalEntity>? {
        return try {
            ResponseEntity.ok().body(getById(id))
        } catch (e: EntityNotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e2: JpaObjectRetrievalFailureException) {
            ResponseEntity.notFound().build()
        }
    }

    fun deleteAnimalById(id: Long): ResponseEntity<String> {
        return try {
            animalRepository.deleteById(id)
            ResponseEntity.ok().body("Deletion was successful")
        } catch (e1: EmptyResultDataAccessException) {
            ResponseEntity.notFound().build()
        }
    }
}