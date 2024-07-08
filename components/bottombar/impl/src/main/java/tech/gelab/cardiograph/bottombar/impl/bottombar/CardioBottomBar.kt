package tech.gelab.cardiograph.bottombar.impl.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme

private val BottomAppBarHorizontalPadding = 16.dp - 14.dp
internal val BottomAppBarVerticalPadding = 16.dp
internal val NavigationBarItemHorizontalPadding: Dp = 8.dp

private object BottomBarTokens {
    val ContainerHeight = 64.dp

    val ContentPadding = PaddingValues(
        start = BottomAppBarHorizontalPadding,
        top = BottomAppBarVerticalPadding,
        end = BottomAppBarHorizontalPadding,
        bottom = BottomAppBarVerticalPadding
    )
}

@Composable
fun CardioBottomBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onNavigationItemClick: (NavigationItem) -> Unit,
) {
    Column(modifier) {
        HorizontalDivider()
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BottomBarTokens.ContentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem.entries.forEachIndexed { index, navItem ->
                    BottomBarItem(
                        selected = selectedIndex == index,
                        iconRes = navItem.iconRes,
                        labelRes = navItem.labelRes,
                        onClick = { onNavigationItemClick(navItem) }
                    )
                }
            }
        }
    }
//    BottomAppBar(modifier = modifier) {
//        NavigationItem.entries.forEachIndexed { index, navItem ->
//            NavigationBarItem(
//                selected = index == selectedIndex,
//                colors = colors(),
//                icon = {
//                    Icon(
//                        painter = painterResource(id = navItem.iconRes),
//                        contentDescription = null
//                    )
//                },
//                label = {
//                    Text(
//                        text = stringResource(id = navItem.labelRes),
//                        style = MaterialTheme.typography.labelSmall
//                    )
//                },
//                onClick = { onNavigationItemClick(navItem) },
//            )
//        }
//    }
}

@Preview
@Composable
private fun CardioBottomBarPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            var index by remember {
                mutableIntStateOf(0)
            }
            CardioBottomBar(selectedIndex = index) {
                index = it.ordinal
            }
        }
    }
}