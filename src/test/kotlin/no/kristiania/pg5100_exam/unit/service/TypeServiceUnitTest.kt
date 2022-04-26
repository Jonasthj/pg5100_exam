package no.kristiania.pg5100_exam.unit.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.pg5100_exam.model.TypeEntity
import no.kristiania.pg5100_exam.repository.TypeRepository
import no.kristiania.pg5100_exam.service.TypeService
import org.junit.jupiter.api.Test

class TypeServiceUnitTest {

    /*private val typeRepository = mockk<TypeRepository>()
    private val typeService = TypeService(typeRepository)

    @Test
    fun shouldGetAllTypes(){
        val testType1 = TypeEntity(name = "Mammal")
        val testType2 = TypeEntity(name = "Fish")

        every { typeRepository.findAll() } answers {
            mutableListOf(testType1, testType2)
        }

        val types = typeService.getAllTypes()
        assert(types.size == 2)
        assert(types[0].name == "Mammal")
        assert(types[1].name == "Fish")
    }*/
}