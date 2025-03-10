package dev.runo.harmony.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.runo.harmony.R

@Composable
fun ErrorScreen(errorMessage: String = "Undefined") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_load, errorMessage),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreenNoText() {
    ErrorScreen()
}

@Preview(
    showBackground = true,
    locale = "en",
)
@Composable
fun PreviewErrorScreenTextEN() {
    ErrorScreen("UndefinedReasonException")
}
