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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.openappslabs.fiveg.BuildConfig
import com.openappslabs.fiveg.R
import com.openappslabs.fiveg.ui.components.AboutDivider
import com.openappslabs.fiveg.ui.components.AboutMeCard
import com.openappslabs.fiveg.ui.components.AboutRow
import com.openappslabs.fiveg.ui.components.AboutSectionCard
import com.openappslabs.fiveg.ui.components.Header
import com.openappslabs.fiveg.utils.Constants
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

    val onOpenGithub = remember { { uriHandler.openUri(Constants.Links.GITHUB_ORG) } }
    val onOpenSource = remember { { uriHandler.openUri(Constants.Links.GITHUB_REPO) } }
    val onOpenLicense = remember { { uriHandler.openUri(Constants.Links.LICENSE) } }
    val onSupportEmail = remember {
        {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Constants.Links.SUPPORT_EMAIL.toUri()
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
                title = stringResource(R.string.about_header_title),
                actionIcon = painterResource(id = R.drawable.chevron_left),
                onActionClick = onBackClick,
                actionContentDescription = stringResource(R.string.about_header_action_content_description)
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
                    label = stringResource(R.string.about_app_label),
                    value = stringResource(R.string.about_app_value),
                    iconPainter = painterResource(id = R.drawable.app_icon),
                    showChevron = false,
                    onClick = {}
                )
                AboutDivider()
                AboutRow(
                    label = stringResource(R.string.about_version_label),
                    value = appVersion,
                    iconPainter = painterResource(id = R.drawable.version),
                    showChevron = false,
                    onClick = {}
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = stringResource(R.string.about_org_label),
                    value = stringResource(R.string.about_org_value),
                    iconPainter = painterResource(id = R.drawable.user),
                    onClick = onOpenGithub
                )
                AboutDivider()
                AboutRow(
                    label = stringResource(R.string.about_source_label),
                    value = stringResource(R.string.about_source_value),
                    iconPainter = painterResource(id = R.drawable.code),
                    onClick = onOpenSource
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = stringResource(R.string.about_support_label),
                    value = stringResource(R.string.about_support_value),
                    iconPainter = painterResource(id = R.drawable.mail),
                    onClick = onSupportEmail
                )
                AboutDivider()
                AboutRow(
                    label = stringResource(R.string.about_license_label),
                    value = stringResource(R.string.about_license_value),
                    iconPainter = painterResource(id = R.drawable.scale),
                    onClick = onOpenLicense
                )
            }

            AboutSectionCard {
                AboutRow(
                    label = stringResource(R.string.about_copyright_label),
                    value = stringResource(R.string.about_copyright_value, year),
                    iconPainter = painterResource(id = R.drawable.heart),
                    tint = Color.Red.copy(alpha = 0.7f),
                    onClick = {},
                    showChevron = false
                )
            }
        }
    }
}