package no.kristiania.pg5100_exam.model

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(name = "user_username")
    val username: String? = null,

    @Column(name = "user_password")
    val password: String? = null,

    @Column(name = "user_created")
    val created: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "user_enabled")
    val enabled: Boolean? = true,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    val authorities: MutableList<AuthorityEntity> = mutableListOf()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $username , password = $password , created = $created , enabled = $enabled )"
    }
}
