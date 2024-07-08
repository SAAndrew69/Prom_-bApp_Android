package tech.gelab.cardiograph.authorization.impl.data

import kotlinx.coroutines.flow.Flow
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.api.Profile
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(): AuthService {
    override suspend fun authorize(email: String, password: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(email: String, password: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun logOff(): Boolean {
        TODO("Not yet implemented")
    }

    override fun profileFlow(): Flow<Profile> {
        TODO("Not yet implemented")
    }
}