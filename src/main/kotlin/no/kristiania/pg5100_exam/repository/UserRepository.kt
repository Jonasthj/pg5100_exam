package no.kristiania.pg5100_exam.repository

import no.kristiania.pg5100_exam.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String) : UserEntity?
}