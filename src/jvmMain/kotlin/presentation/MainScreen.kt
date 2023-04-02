package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import presentation.components.InfoScreen
import presentation.components.ReceivingDataScreen
import presentation.components.SettingsScreen

@Composable
fun MainScreen(viewModel: AppViewModel) {
    val serialPortName by remember { mutableStateOf(viewModel.serialPortName) }
    val numberOfLines by remember { mutableStateOf(viewModel.numberOfLines) }
    val numberOfReceivedLines by remember { mutableStateOf(viewModel.numberOfReceivedLines) }
    val path by remember { mutableStateOf(viewModel.path) }
    val progress = remember { mutableStateOf(0f) }
    val isStarted = remember { mutableStateOf(false) }
    val isError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf(String()) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            if(numberOfLines.value > 0.toUInt())
                progress.value = numberOfReceivedLines.value.toFloat() / numberOfLines.value.toFloat()
            else
                progress.value = 0f

            if(isStarted.value && numberOfReceivedLines.value >= numberOfLines.value) {
                viewModel.close()
                isStarted.value = false
            }

            if (isStarted.value)
                ReceivingDataScreen(
                    viewModel,
                    numberOfLines,
                    numberOfReceivedLines,
                    isStarted,
                    progress
                )
            else if (isError.value || path.value != String())
                InfoScreen(
                    isError,
                    errorMessage,
                    path,
                    onClick = {
                        viewModel.resetPath()
                    }
                )
            else
                SettingsScreen(
                    viewModel,
                    serialPortName,
                    numberOfLines,
                    isStarted,
                    isError,
                    errorMessage
                )
        }
    }
}