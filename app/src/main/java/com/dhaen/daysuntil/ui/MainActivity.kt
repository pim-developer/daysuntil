package com.dhaen.daysuntil.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhaen.daysuntil.R
import com.dhaen.daysuntil.ui.home.HomeScreenRoute
import com.dhaen.daysuntil.ui.theme.DaysUntilTheme
import com.dhaen.daysuntil.ui.theme.tertiaryContainerLight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val statusBarColor = tertiaryContainerLight.toArgb()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(statusBarColor),
            navigationBarStyle = SystemBarStyle.dark(statusBarColor),
        )
        setContent {
            val (homeScreenFABButtonOnClick, setHomeScreenFABButtonOnClick) = remember {
                mutableStateOf<(() -> Unit)?>(
                    null
                )
            }

            Root(homeScreenFabOnClick = homeScreenFABButtonOnClick) {
                HomeScreenRoute(setHomeScreenFABButtonOnClick = setHomeScreenFABButtonOnClick)
            }
        }
    }
}

@Composable
private fun Root(
    modifier: Modifier = Modifier,
    homeScreenFabOnClick: (() -> Unit)?,
    rootContent: @Composable () -> Unit
) {
    DaysUntilTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        homeScreenFabOnClick?.invoke()
                    },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    Text(
                        text = stringResource(id = R.string.add_new_countdown_event_fab_button_text),
                        style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp)
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                rootContent()
            }
        }
    }
}

// TODO: change to preview root
@Preview
@Composable
private fun PreviewScaffold() {
    DaysUntilTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = {  },
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_new_countdown_event_fab_button_text),
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "the center of the screen")
                    }
                }

            }
        }
    }
}
