package project.price_it.dto.user

import project.price_it.entity.UserEntity

data class UserDto(
        val id: Long? = null,
        val email: String,
        val password: String,
        val name: String = "example",
        val lat: Double = 0.0,
        val lng: Double = 0.0,
        val point: Int = 1000
) {
    fun toEntity(): UserEntity {
        return UserEntity(
                id = id,
                name = name,
                lat = lat,
                lng = lng,
                point = point,
                email = email,
                password = password
        )
    }

    companion object {
        fun fromEntity(user: UserEntity): UserDto {
            return UserDto(
                    id = user.id,
                    name = user.name ?: "example",
                    point = user.point,
                    email = user.email,
                    lat = user.lat ?: 0.0,
                    lng = user.lng ?: 0.0,
                    password = user.password
            )
        }
    }
}
