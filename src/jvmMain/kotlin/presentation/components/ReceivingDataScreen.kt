package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.AppViewModel

@Composable
fun ReceivingDataScreen(
    viewModel: AppViewModel,
    numberOfLines: State<UInt>,
    numberOfReceivedLines: State<UInt>,
    isStarted: MutableState<Boolean>,
    progress: MutableState<Float>
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress.value,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = numberOfReceivedLines.value.toString() + " / " +  numberOfLines.value.toString(),
            textAlign= TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                viewModel.close()
                isStarted.value = false
            },
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text("Stop")
        }
    }
}