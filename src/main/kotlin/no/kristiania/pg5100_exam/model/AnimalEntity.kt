package no.kristiania.pg5100_exam.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "animals")
data class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_animal_id_seq")
    @SequenceGenerator(name = "animals_animal_id_seq", allocationSize = 1)
    @Column(name = "animal_id")
    val id: Long? = null,

    @Column(name = "animal_name")
    val name: String?,

    @Column(name = "animal_age")
    val age: Int?,

    @Column(name = "animal_weight")
    val weight: Float?,

    @Column(name = "animal_breed_id")
    val breedId: Int?,

    @Column(name = "animal_type_id")
    val typeId: Int?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AnimalEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , age = $age , weight = $weight , breedId = $breedId , typeId = $typeId )"
    }
}