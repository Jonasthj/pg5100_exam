package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AnimalRepository : JpaRepository<AnimalEntity, Long>