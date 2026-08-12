package com.openappslabs.fiveg.ui.screens.aboutscreen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.openappslabs.fiveg.BuildConfig
import com.openappslabs.fiveg.R
import com.openappslabs.fiveg.ui.components.AboutDivider
import com.openappslabs.fiveg.ui.components.AboutMeCard
import com.openappslabs.fiveg.ui.components.AboutRow
import com.openappslabs.fiveg.ui.components.AboutSectionCard
import com.openappslabs.fiveg.ui.components.Header
import java.time.Year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val year = remember { Year.now().toString() }
    val appVersion = remember { BuildConfig.VERSION_NAME }
    val copyrightText = remember { "Open Apps Labs © $year" }

    val onOpenGithub = remember { { uriHandler.openUri("https://github.com/OpenAppsLabs") } }
    val onOpenSource = remember { { uriHandler.openUri("https://github.com/OpenAppsLabs/5G") } }
    val onOpenLicense = remember { { uriHandler.openUri("https://www.gnu.org/licenses/gpl-3.0.en.html") } }
    val onSupportEmail = remember {
        {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:openappslabs@gmail.com".toUri()
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
            }
        }
    }

    Scaffold(
        topBar = {
            Header(
                title = "About",
                actionIcon = painterResource(id = R.drawable.chevron_left),
                onActionClick = onBackClick,
                actionContentDescription = "Back"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AboutMeCard()

            AboutSectionCard {
                AboutRow(
                    label = "APP",
                    value = "5G",
                    iconPainter = painterResource(id = R.drawable.app_icon),
                    showChevron = false,
                    onClick = {}
                )
                AboutDivider()
                AboutRow(
                    label = "VERSION",
                    value = appVersion,
                    iconPainter = painterResource(id = R.drawable.version),
                    showChevron = false,
                    onClick = {}
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = "ORGANIZATION",
                    value = "Open Apps Labs",
                    iconPainter = painterResource(id = R.drawable.user),
                    onClick = onOpenGithub
                )
                AboutDivider()
                AboutRow(
                    label = "SOURCE CODE",
                    value = "5G",
                    iconPainter = painterResource(id = R.drawable.code),
                    onClick = onOpenSource
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = "SUPPORT",
                    value = "openappslabs@gmail.com",
                    iconPainter = painterResource(id = R.drawable.mail),
                    onClick = onSupportEmail
                )
                AboutDivider()
                AboutRow(
                    label = "LICENSE",
                    value = "GNU GPL v3.0",
                    iconPainter = painterResource(id = R.drawable.scale),
                    onClick = onOpenLicense
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = "MADE WITH LOVE",
                    value = copyrightText,
                    iconPainter = painterResource(id = R.drawable.heart),
                    tint = Color.Red.copy(alpha = 0.7f),
                    onClick = {},
                    showChevron = false
                )
            }
        }
    }
}