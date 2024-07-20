package tech.gelab.cardiograph.singleactivity.impl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toPersistentSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.BottomSheetFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SingleActivity : ComponentActivity() {

    @Inject
    internal lateinit var deeplinkHelper: DeeplinkHelper

    @Inject
    internal lateinit var composableEntriesMutable: MutableSet<@JvmSuppressWildcards ComposableFeatureEntry>

    @Inject
    internal lateinit var aggregateEntriesMutable: MutableSet<@JvmSuppressWildcards AggregateFeatureEntry>

    @Inject
    internal lateinit var bottomSheetEntriesMutable: MutableSet<@JvmSuppressWildcards BottomSheetFeatureEntry>

    private var globalNavController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composableEntries = composableEntriesMutable.toPersistentSet()
        val aggregateEntries = aggregateEntriesMutable.toPersistentSet()
        val bottomSheetEntries = bottomSheetEntriesMutable.toPersistentSet()
        Timber.i("onCreate: intent = $intent")

        setContent {
            val navControllerLocal = rememberNavController()
                .also { globalNavController = it }

            CardiographAppTheme {
                CompositionLocalProvider(value = LocalNavHostProvider provides navControllerLocal) {
                    Navigation(
                        navController = navControllerLocal,
                        startDestination = deeplinkHelper.getStartDestination(),
                        composableEntries = composableEntries,
                        aggregateEntries = aggregateEntries,
                        bottomSheetEntries = bottomSheetEntries
                    )
                }
            }
        }
    }
}