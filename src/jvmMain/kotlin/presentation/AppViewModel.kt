package presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import core.SerialReader

class AppViewModel {
    private val serialReader: SerialReader

    private var _numberOfLines = mutableStateOf(10.toUInt())
    val numberOfLines: State<UInt> = _numberOfLines

    private var _numberOfReceivedLines = mutableStateOf(0.toUInt())
    val numberOfReceivedLines: State<UInt> = _numberOfReceivedLines

    private var _serialPortName = mutableStateOf("COM1")
    val serialPortName: State<String> = _serialPortName

    private var _path = mutableStateOf(String())
    val path: State<String> = _path

    init {
        serialReader = SerialReader(
            onReceive = { numberOfReceivedLines ->
                _numberOfReceivedLines.value = numberOfReceivedLines
            },
            path = { path ->
                _path.value = path
            }
        )
    }

    fun start(status: (String) -> Unit) {
        serialReader.start(serialPortName.value, numberOfLines.value) { status(it) }
    }

    fun close() {
        _numberOfReceivedLines.value = 0u
        serialReader.close(
            path = { path ->
                _path.value = path
            }
        )
    }

    fun setSerialPortName(text: String) {
        _serialPortName.value = text
    }

    fun setNumberOfLines(number: UInt) {
        _numberOfLines.value = number
    }

    fun resetPath() {
        _path.value = String()
    }
}
