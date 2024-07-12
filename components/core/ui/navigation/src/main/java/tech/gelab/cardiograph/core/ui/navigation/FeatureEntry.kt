package tech.gelab.cardiograph.core.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureEntry {

    @Suppress("PropertyName")
    val ROUTE: NavigationRoute

}

interface ComposableFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.composable(navController: NavController)

}

interface AggregateFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.navigation(navController: NavController)

}

interface BottomSheetFeatureEntry : FeatureEntry {

    fun NavGraphBuilder.bottomSheet(navController: NavController)

}