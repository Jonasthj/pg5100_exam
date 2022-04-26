package no.kristiania.pg5100_exam.model

import org.hibernate.Hibernate
import javax.persistence.*


@Entity
@Table(name = "types")
data class TypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "types_type_id_seq")
    @SequenceGenerator(name = "types_type_id_seq", allocationSize = 1)
    @Column(name = "type_id")
    val id: Long? = null,

    @Column(name = "type_name")
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TypeEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}