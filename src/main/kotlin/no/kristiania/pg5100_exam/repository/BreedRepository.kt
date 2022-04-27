package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.BreedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BreedRepository : JpaRepository<BreedEntity, Long>