package project.price_it.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import project.price_it.entity.UserEntity
import project.price_it.repository.UserRepository

@Service
open class UserService(
        private val userRepository: UserRepository
) {

    @Transactional
    open fun create(entity: UserEntity): UserEntity {
        if (userRepository.findByEmail(entity.email).isPresent) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다: ${entity.email}")
        }
        return userRepository.save(entity)
    }

    fun get(id: Long): UserEntity {
        // getById는 Lazy 조회이므로 필요에 따라 findById 사용
        return userRepository.findById(id)
                .orElseThrow { IllegalArgumentException("사용자 없음") }
    }

    fun authenticate(email: String, password: String): UserEntity {
        val user = userRepository.findByEmail(email)
                .orElseThrow { IllegalArgumentException("사용자 없음") }

        if (user.password != password) {
            throw IllegalArgumentException("비밀번호 불일치")
        }

        return user
    }
}
