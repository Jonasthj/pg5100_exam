package no.kristiania.pg5100_exam.controller

import no.kristiania.pg5100_exam.model.AnimalEntity
import no.kristiania.pg5100_exam.service.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/api/shelter")
class AnimalController(@Autowired private val animalService: AnimalService) {

    @GetMapping("/all")
    fun retrieveAllAnimals() : ResponseEntity<List<AnimalEntity?>> {
        return ResponseEntity.ok().body(animalService.getAllAnimals())
    }

    @GetMapping("/get/{id}")
    fun retrieveSingleAnimal(@PathVariable id: Long) : ResponseEntity<AnimalEntity>? {
        return animalService.getResponseById(id)
    }

    @PostMapping("/add")
    fun addNewAnimal(@RequestBody newAnimal: NewAnimal) : ResponseEntity<AnimalEntity> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter").toUriString())
        return ResponseEntity.created(uri).body(animalService.addNewAnimal(newAnimal))
    }

    @PutMapping("/update/{id}")
    fun updateAnimal(
        @PathVariable id: Long,
        @RequestBody newAnimal: NewAnimal
    ): ResponseEntity<AnimalEntity>? {

        return animalService.updateAnimalById(id, newAnimal)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteAnimal(@PathVariable id: Long) : ResponseEntity<String> {
        return animalService.deleteAnimalById(id)
    }



    data class NewAnimal(
        val name: String? = null,
        val age: Int? = null,
        val weight: Float? = null,
        val typeId: Int? = null,
        val breedId: Int? = null
    )
}