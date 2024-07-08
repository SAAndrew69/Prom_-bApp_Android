package tech.gelab.cardiograph.authorization.impl.domain

import androidx.annotation.StringRes

data class AuthButtonModel<ScreenEvent>(
    val buttonType: ButtonType,
    @StringRes val labelId: Int,
    val buttonEvent: ScreenEvent,
    val enabled: Boolean = true
)