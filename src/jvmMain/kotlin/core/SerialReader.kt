package core

import gnu.io.*
import java.io.BufferedReader
import java.io.InputStreamReader

class SerialReader(
    private val onReceive: (UInt) -> Unit,
    private val path: (String) -> Unit
) : SerialPortEventListener {

    private var serialPort: SerialPort? = null
    private var inputDataStream: BufferedReader? = null
    private var numberOfReceivedLines = 0.toUInt()
    private var numberOfLines = 0.toUInt()
    private var excel: Excel? = null

    override fun serialEvent(event: SerialPortEvent) {
        if (event.eventType == SerialPortEvent.DATA_AVAILABLE) {
            try {
                inputDataStream?.let { stream ->
                    if(stream.ready()) {
                        val text = stream.readLine()
                        if (text != null) {
                            excel?.writeRow(text)

                            numberOfReceivedLines++

                            onReceive(numberOfReceivedLines)

                            if (numberOfReceivedLines >= numberOfLines) {
                                serialPort?.removeEventListener()
                                close { filePath ->
                                        path(filePath)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(Exception::class)
    fun start(serialPortName: String, numberOfLines: UInt, status: (String) -> Unit) {
        try {
            this.numberOfLines = numberOfLines

            val portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName)

            if (portIdentifier.isCurrentlyOwned) {
                throw Exception("Der serielle Port ist bereits beschäftigt.")
            }

            serialPort = portIdentifier.open(this.javaClass.name, 2000) as SerialPort

            serialPort?.let { serialPort ->
                val inputStream = serialPort.inputStream

                inputDataStream = BufferedReader(InputStreamReader(inputStream))

                serialPort.setSerialPortParams(
                    115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
                )

                excel = Excel()

                serialPort.addEventListener(this)
                serialPort.notifyOnDataAvailable(true)

                numberOfReceivedLines = 0.toUInt()

                status("OK")
            }
        } catch (e: NoSuchPortException) {
            status("NoSuchPortException")
        }
    }

    fun close(path: (String) -> Unit) {
        excel?.close(
            filePath = { filePath ->
                path(filePath)
            }
        )

        serialPort?.removeEventListener()
        serialPort?.close()
    }
}
