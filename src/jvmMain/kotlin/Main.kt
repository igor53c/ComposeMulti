import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import presentation.AppViewModel
import presentation.MainScreen

fun main() = application {
    val viewModel = remember { AppViewModel() }

    Window(
        title = "SerialPortExcel",
        onCloseRequest =
        {
            viewModel.close()
            exitApplication()
        },
        icon = painterResource("drawables/launcher_icons/icon.ico"),
        state = WindowState(
            size = DpSize(500.dp, 250.dp),
            position = WindowPosition(alignment = Alignment.Center),
        )
    ) {
        MainScreen(viewModel = viewModel)
    }
}
