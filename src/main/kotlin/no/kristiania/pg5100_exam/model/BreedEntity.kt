package no.kristiania.pg5100_exam.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "breeds")
data class BreedEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "breeds_breed_id_seq")
    @SequenceGenerator(name = "breeds_breed_id_seq", allocationSize = 1)
    @Column(name = "breed_id")
    val id: Long? = null,

    @Column(name = "breed_name")
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as BreedEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}