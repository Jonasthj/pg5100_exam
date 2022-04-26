package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AnimalRepository : JpaRepository<AnimalEntity, Long> {

    fun getByName(name: String): AnimalEntity?


    @Transactional
    @Modifying
    @Query(
        "UPDATE AnimalEntity a set " +
                "a.name = ?1, " +
                "a.age = ?2, " +
                "a.weight = ?3, " +
                "a.typeId = ?4, " +
                "a.breedId = ?5 " +
                "WHERE a.id = ?6")

    fun updateAnimalById(name: String?, age: Int?, weight: Float?, typeId: Int?, breedId: Int?, id: Long?) : Int

}