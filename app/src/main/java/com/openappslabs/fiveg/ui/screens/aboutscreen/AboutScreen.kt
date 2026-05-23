package com.openappslabs.fiveg.ui.screens.aboutscreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.openappslabs.fiveg.R
import com.openappslabs.fiveg.ui.components.AboutMeCard
import com.openappslabs.fiveg.BuildConfig
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
            TopAppBar(
                title = {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        letterSpacing = (-0.5).sp
                    )
                },
                actions = {
                    Surface(
                        onClick = onBackClick,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp)
                            .size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.chevron_left),
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
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

            CardSection {
                InfoRow(
                    label = "APP",
                    value = "5G",
                    iconPainter = painterResource(id = R.drawable.app_icon),
                    showChevron = false,
                    onClick = {}
                )
                InfoDivider()
                InfoRow(
                    label = "VERSION",
                    value = appVersion,
                    iconPainter = painterResource(id = R.drawable.version),
                    showChevron = false,
                    onClick = {}
                )
            }

            CardSection {
                InfoRow(
                    label = "ORGANIZATION",
                    value = "Open Apps Labs",
                    iconPainter = painterResource(id = R.drawable.user),
                    onClick = onOpenGithub
                )
                InfoDivider()
                InfoRow(
                    label = "SOURCE CODE",
                    value = "5G",
                    iconPainter = painterResource(id = R.drawable.code),
                    onClick = onOpenSource
                )
            }

            CardSection {
                InfoRow(
                    label = "SUPPORT",
                    value = "openappslabs@gmail.com",
                    iconPainter = painterResource(id = R.drawable.mail),
                    onClick = onSupportEmail
                )
                InfoDivider()
                InfoRow(
                    label = "LICENSE",
                    value = "GNU GPL v3.0",
                    iconPainter = painterResource(id = R.drawable.scale),
                    onClick = onOpenLicense
                )
            }

            CardSection {
                InfoRow(
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

@Composable
private fun CardSection(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    iconPainter: Painter,
    tint: Color = MaterialTheme.colorScheme.primary,
    showChevron: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = tint
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp
                ),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = (-0.25).sp,
                lineHeight = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (showChevron) {
            Icon(
                painter = painterResource(id = R.drawable.external_link),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun InfoDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.25.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}