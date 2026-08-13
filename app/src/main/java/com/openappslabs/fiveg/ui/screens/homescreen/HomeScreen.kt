package com.openappslabs.fiveg.ui.screens.homescreen

import android.app.StatusBarManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.core.content.getSystemService
import com.openappslabs.fiveg.R
import com.openappslabs.fiveg.services.FiveGTileService
import com.openappslabs.fiveg.ui.components.BottomBar
import com.openappslabs.fiveg.ui.components.Header
import com.openappslabs.fiveg.ui.components.InfoCard
import com.openappslabs.fiveg.ui.components.NetworkSettingsButton
import com.openappslabs.fiveg.ui.components.StepItem
import com.openappslabs.fiveg.utils.RadioInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAboutClick: () -> Unit,
) {
    val context = LocalContext.current
    val appName = stringResource(R.string.app_name)
    val tileComponent = remember(context) { ComponentName(context, FiveGTileService::class.java) }
    val onOpenSettingsClick = remember(context) { { RadioInfo.openRadioInfo(context) } }
    val onAddTileClick: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val statusBarManager = context.getSystemService<StatusBarManager>()
            statusBarManager?.requestAddTileService(
                tileComponent,
                appName,
                Icon.createWithResource(context, R.drawable.app_icon),
                context.mainExecutor
            ) { _ -> }
        }
    }

    Scaffold(
        topBar = {
            Header(
                title = stringResource(R.string.home_header_title),
                actionIcon = painterResource(id = R.drawable.info),
                onActionClick = onAboutClick,
                actionContentDescription = stringResource(R.string.home_header_action_content_description)
            )
        },
        bottomBar = {
            BottomBar(
                onAddTileClick = onAddTileClick,
                onOpenSettingsClick = onOpenSettingsClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                label = stringResource(R.string.guide_label),
                title = stringResource(R.string.guide_title),
                icon = painterResource(id = R.drawable.badge_info)
            ) {
                StepItem(number = "1", text = stringResource(R.string.guide_step_1))
                StepItem(number = "2", text = stringResource(R.string.guide_step_2))
                StepItem(number = "3", text = stringResource(R.string.guide_step_3))
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(
                label = stringResource(R.string.notice_label),
                title = stringResource(R.string.notice_title),
                icon = painterResource(id = R.drawable.alert),
                accentColor = MaterialTheme.colorScheme.error
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.notice_content),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NetworkSettingsButton(
                        onClick = { RadioInfo.openNetworkSettings(context) }
                    )
                }
            }
        }
    }
}