package tech.gelab.cardiograph.authorization.impl.domain.usecase

import kotlinx.coroutines.delay
import tech.gelab.cardiograph.authorization.api.AuthService
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(private val authService: AuthService) {

    suspend fun invoke(): Result<Boolean> {
        return try {
            delay(3000)
            TODO("Not yet implemented")
            Result.success(true)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

}