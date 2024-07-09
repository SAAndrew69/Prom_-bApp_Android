package tech.gelab.cardiograph.authorization.impl.data

import kotlinx.coroutines.flow.Flow
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.api.Profile
import java.lang.RuntimeException
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(): AuthService {
    override suspend fun authorize(email: String, password: String): Result<String> {
        return try {
            throw RuntimeException("Not yet implemented")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<String> {
        return try {
            throw RuntimeException("Not yet implemented")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logOff(): Boolean {
        TODO("Not yet implemented")
    }

    override fun profileFlow(): Flow<Profile> {
        TODO("Not yet implemented")
    }
}