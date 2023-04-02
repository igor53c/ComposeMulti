package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(
    isError: MutableState<Boolean>,
    errorMessage: MutableState<String>,
    path: State<String>,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = if(isError.value) errorMessage.value else "Datei gespeichert: \n" + path.value

        Text(
            text = text,
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                isError.value = false
                errorMessage.value = String()
                onClick()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text("OK")
        }
    }
}