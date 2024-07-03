package tech.gelab.cardiograph.singleactivity.impl

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toPersistentSet
import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.navigation.NavigationRoute
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SingleActivity : ComponentActivity() {

    @Inject
    internal lateinit var composableEntriessMutable: MutableSet<@JvmSuppressWildcards ComposableFeatureEntry>

    private var globalNavController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composableEntries = composableEntriessMutable.toPersistentSet()

        Timber.i("onCreate: intent = $intent")

        setContent {
            val navControllerLocal = rememberNavController().also { globalNavController = it }

            Navigation(
                navController = navControllerLocal,
                // TODO check if pair already exists
                startDestination = NavigationRoute.AUTHORIZATION.name,
                composableEntries = composableEntries
            )

        }
    }

    @Composable
    fun Content() {

    }

}