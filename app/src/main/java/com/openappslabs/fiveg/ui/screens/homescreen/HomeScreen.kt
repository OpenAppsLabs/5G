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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.core.content.getSystemService
import com.openappslabs.fiveg.R
import com.openappslabs.fiveg.services.FiveGTileService
import com.openappslabs.fiveg.ui.components.BottomBar
import com.openappslabs.fiveg.ui.components.Header
import com.openappslabs.fiveg.ui.components.InfoCard
import com.openappslabs.fiveg.ui.components.StepItem
import com.openappslabs.fiveg.utils.RadioInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAboutClick: () -> Unit,
) {
    val context = LocalContext.current
    val tileComponent = remember(context) { ComponentName(context, FiveGTileService::class.java) }
    val onOpenSettingsClick = { RadioInfo.openRadioInfo(context) }
    val onAddTileClick: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val statusBarManager = context.getSystemService<StatusBarManager>()

            statusBarManager?.requestAddTileService(
                tileComponent,
                "5G",
                Icon.createWithResource(context, R.drawable.app_icon),
                context.mainExecutor
            ) { _ -> }
        }
    }

    Scaffold(
        topBar = {
            Header(
                title = "5G",
                actionIcon = painterResource(id = R.drawable.info),
                onActionClick = onAboutClick,
                actionContentDescription = "About"
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
                label = "GUIDE",
                title = "Steps to Enable 5G",
                icon = painterResource(id = R.drawable.badge_info)
            ) {
                StepItem(number = "1", text = "Tap 'Open 5G Settings' below.")
                StepItem(number = "2", text = "Scroll down to 'Set Preferred Network Type'.")
                StepItem(number = "3", text = "Select 'NR Only'.")
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(
                label = "NOTICE",
                title = "Important Note",
                icon = painterResource(id = R.drawable.alert),
                accentColor = MaterialTheme.colorScheme.error
            ) {
                Column {
                    Text(
                        text = "In case you don't see the signal, tap the button below and set network type to 'LTE/4G'. Else it works just fine.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { RadioInfo.openNetworkSettings(context) },
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
                        )
                    ) {
                        Text(
                            text = "Network Settings",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}