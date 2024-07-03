package tech.gelab.cardiograph.authorization.api

import kotlinx.coroutines.flow.Flow

interface AuthService {

    /**
     * Tries to get authorization token with given email and password
     * @return Result with token or Result with exception in case of failure
     */
    suspend fun authorize(email: String, password: String): Result<String>

    /**
     * Log off from current profile
     * @return boolean representing success of the operation
     */
    suspend fun logOff(): Boolean

    /**
     * Flow with profile updates
     */
    fun profileFlow(): Flow<Profile>

}