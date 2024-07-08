package tech.gelab.cardiograph.authorization.signup.domain

import tech.gelab.cardiograph.authorization.api.AuthService
import timber.log.Timber
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authService: AuthService) {

    suspend fun invoke(email: String, password: String): Result<String> {
        return try {
            authService.signUp(email, password)
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }

}