package tech.gelab.cardiograph.authorization.impl.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.api.Profile
import tech.gelab.cardiograph.storage.pb.AuthorizationSettings
import java.lang.RuntimeException
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authSettings: DataStore<AuthorizationSettings>
): AuthService {
    override fun shouldOpenAuthScreen(): Boolean {
        return runBlocking { !authSettings.data.first().authenticationComplete }
    }

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