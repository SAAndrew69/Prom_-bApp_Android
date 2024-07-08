package tech.gelab.cardiograph.singleactivity.impl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SingleActivity : ComponentActivity() {

    @Inject
    internal lateinit var composableEntriesMutable: MutableSet<@JvmSuppressWildcards ComposableFeatureEntry>

    @Inject
    internal lateinit var aggregateEntriesMutable: MutableSet<@JvmSuppressWildcards AggregateFeatureEntry>

    private var globalNavController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composableEntries = composableEntriesMutable.toPersistentSet()
        val aggregateEntries = aggregateEntriesMutable.toPersistentSet()
        Timber.i("onCreate: intent = $intent")

        setContent {
            val navControllerLocal = rememberNavController().also { globalNavController = it }
            CardiographAppTheme {
                CompositionLocalProvider(value = LocalNavHostProvider provides navControllerLocal) {
                    Navigation(
                        navController = navControllerLocal,
                        // TODO check if pair already exists
                        startDestination = NavigationRoute.WELCOME.name,
                        composableEntries = composableEntries,
                        aggregateEntries = aggregateEntries
                    )
                }
            }
        }
    }
}