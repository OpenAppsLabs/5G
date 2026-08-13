package com.openappslabs.fiveg.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openappslabs.fiveg.R

@Composable
fun NetworkSettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
        )
    ) {
        Text(
            text = stringResource(R.string.network_settings_button),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}