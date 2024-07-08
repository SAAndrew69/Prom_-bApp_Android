package tech.gelab.cardiograph.authorization.signup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.signup.SignUpFeatureEntryImpl
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindSignUpFeatureEntry(signUpFeatureEntryImpl: SignUpFeatureEntryImpl): SignUpFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(signUpFeatureEntry: SignUpFeatureEntry): ComposableFeatureEntry

}