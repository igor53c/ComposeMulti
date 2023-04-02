package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.AppViewModel

@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    serialPortName: State<String>,
    numberOfLines: State<UInt>,
    isStarted: MutableState<Boolean>,
    isError: MutableState<Boolean>,
    errorMessage: MutableState<String>
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = serialPortName.value,
            onValueChange = { viewModel.setSerialPortName(it) },
            label = { Text("Geben Sie den seriellen Port ein:") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = numberOfLines.value.toString(),
            onValueChange =
            { value ->
                val intValue = try {
                    value.toUInt()
                } catch (e: NumberFormatException) {
                    0u
                }

                if (intValue > 0.toUInt()) {
                    viewModel.setNumberOfLines(intValue)
                }
            },
            label = { Text("Anzahl der Zeilen:") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.start { status ->
                    when(status) {
                        "OK" -> {
                            isStarted.value = true
                            isError.value = false
                            errorMessage.value = String()
                        }
                        "NoSuchPortException" -> {
                            isStarted.value = false
                            isError.value = true
                            errorMessage.value = "Die serielle Schnittstelle mit diesem Namen kann nicht geöffnet werden"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Start")
        }
    }
}