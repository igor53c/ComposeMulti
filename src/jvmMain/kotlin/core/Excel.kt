package core

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Excel {
    private var workbook: Workbook? = null
    private var sheet: Sheet? = null
    private var rowNumber = 0

    init {
        try {
            workbook = XSSFWorkbook()
            sheet = workbook!!.createSheet("Ergebnisse")
            rowNumber = 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun writeRow(rowText: String?) {
        val row: Row = sheet!!.createRow(rowNumber)
        val texts = rowText!!.split("\t").toTypedArray()

        for (i in texts.indices) {
            val cell: Cell = row.createCell(i)
            cell.setCellValue(texts[i])
        }

        rowNumber++
    }

    fun close(filePath: (String) -> Unit) {
        try {
            workbook?.let { workbook ->
                if (rowNumber > 0) {
                    var path = "${System.getProperty("user.home")}\\Desktop"
                    path += File.separator + "SerialPortExcel.xlsx"
                    var file = File(path)
                    var number = 0

                    while (file.exists()) {
                        number++
                        path = "${System.getProperty("user.home")}\\Desktop"
                        path += File.separator + "SerialPortExcel$number.xlsx"
                        file = File(path)
                    }

                    filePath(path)

                    val outputStream = FileOutputStream(file)
                    workbook.write(outputStream)
                    workbook.close()
                    outputStream.close()
                    this.workbook = null
                }
            }
        } catch (e: IOException) {
            filePath(String())
        }
    }
}