package tech.gelab.cardiograph.authorization.signup.domain

sealed interface SignUpScreenEvent {
    data class TextFieldUpdate(val textFieldType: TextFieldType, val value: String) :
        SignUpScreenEvent
    data class TextFieldSubmit(val textFieldType: TextFieldType) : SignUpScreenEvent
    data object VisibilityUpdate : SignUpScreenEvent
    data object RegisterClick : SignUpScreenEvent
    data object SkipClick : SignUpScreenEvent
    data object BackClick : SignUpScreenEvent

    enum class TextFieldType {
        EMAIL,
        PASSWORD,
        PASSWORD_CONFIRM
    }
}