package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository : JpaRepository<AnimalEntity, Long> {

    @Query(
        value = "SELECT a.animal_id, a.animal_name, a.animal_age, a.animal_weight, t.type_name, b.breed_name " +
                "FROM animals a " +
                "JOIN types t on a.animal_type_id = t.type_id " +
                "JOIN breeds b on a.animal_breed_id = b.breed_id ",
        nativeQuery = true
    )
    fun getDisplayAnimals() : List<Any>
}