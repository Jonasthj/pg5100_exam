package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository:JpaRepository<AuthorityEntity, Long> {

    fun getByAuthorityName(authorityName: String): AuthorityEntity?
}