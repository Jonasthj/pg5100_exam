package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.TypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeRepository: JpaRepository<TypeEntity, Long>