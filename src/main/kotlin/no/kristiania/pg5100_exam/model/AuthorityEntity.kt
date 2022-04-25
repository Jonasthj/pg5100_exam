package no.kristiania.pg5100_exam.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "authorities")
data class AuthorityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_authority_id_seq")
    @SequenceGenerator(name = "authorities_authority_id_seq", allocationSize = 1)
    @Column(name = "authority_id")
    val id: Long? = null,

    @Column(name = "authority_name")
    val authorityName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AuthorityEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}