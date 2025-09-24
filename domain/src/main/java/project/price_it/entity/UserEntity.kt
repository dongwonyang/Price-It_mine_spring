package project.price_it.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.Setter
import lombok.ToString

@Entity
@ToString
@Builder
@Getter
@Setter
@Table(name = "user_entity")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column
        var email: String,

        @Column
        var password: String,

        @Column
        var name: String,

        @Column
        var point: Int = 0,

        @Column
        var lat: Double? = null,

        @Column
        var lng: Double? = null
)
