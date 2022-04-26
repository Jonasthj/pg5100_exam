package no.kristiania.pg5100_exam.service

import no.kristiania.pg5100_exam.model.TypeEntity
import no.kristiania.pg5100_exam.repository.TypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TypeService(@Autowired private val typeRepository: TypeRepository) {

    /*fun getAllTypes() : List<TypeEntity> {
        return typeRepository.findAll()
    }*/
}