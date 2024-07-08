package tech.gelab.cardiograph.authorization.api

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry

interface AuthApi {

    @Composable
    fun EmailTextField(
        modifier: Modifier,
        email: String,
        enabled: Boolean,
        isValid: String,
        onUpdate: (String) -> Unit,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions
    )

    @Composable
    fun PasswordTextField(
        modifier: Modifier,
        password: String,
        visible: Boolean,
        enabled: Boolean,
        @StringRes labelResource: Int?,
        @StringRes placeholderResource: Int,
        updateInput: (String) -> Unit,
        onVisibilityIconClick: () -> Unit,
        onDone: () -> Unit,
        errorMessage: String?
    )

    @Composable
    fun HeartbeatWithText(modifier: Modifier, text: String)

    fun enterTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)

}