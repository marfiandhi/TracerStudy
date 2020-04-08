package id.divascion.tracerstudy.util

import android.os.Environment
import android.util.Log.e
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.format.Alignment
import jxl.write.*
import jxl.write.biff.RowsExceededException
import java.io.File
import java.io.IOException

private const val TAG = "Excel"

class Excel {

    fun createWorkbook(fileName: String): WritableWorkbook? {
        //exports must use a temp file while writing to avoid memory hogging
        val wbSettings = WorkbookSettings()
        wbSettings.useTemporaryFileDuringWrite = true

        //get the sdcard's directory
        val sdCard = Environment.getExternalStorageDirectory()
        //add on the your app's path
        val dir = File(sdCard.absolutePath + "/TracerStudy")
        //make them in case they're not there
        dir.mkdirs()
        //create a standard java.io.File object for the Workbook to use
        val wbFile = File(dir, fileName)

        var wb: WritableWorkbook? = null

        try {
            //create a new WritableWorkbook using the java.io.File and
            //WorkbookSettings from above
            wb = Workbook.createWorkbook(wbFile, wbSettings)
        } catch (ex: IOException) {
            e(TAG, ex.stackTrace.toString())
            e(TAG, ex.message)
        }

        return wb
    }

    /**
     *
     * @param wb - WritableWorkbook to create new sheet in
     * @param sheetName - name to be given to new sheet
     * @param sheetIndex - position in sheet tabs at bottom of workbook
     * @return - a new WritableSheet in given WritableWorkbook
     */
    fun createSheet(
        wb: WritableWorkbook,
        sheetName: String, sheetIndex: Int
    ): WritableSheet {
        //create a new WritableSheet and return it
        return wb.createSheet(sheetName, sheetIndex)
    }

    /**
     *
     * @param columnPosition - column to place new cell in
     * @param rowPosition - row to place new cell in
     * @param contents - string value to place in cell
     * @param headerCell - whether to give this cell special formatting
     * @param sheet - WritableSheet to place cell in
     * @throws RowsExceededException - thrown if adding cell exceeds .xls row limit
     * @throws WriteException - Idunno, might be thrown
     */
    @Suppress("INACCESSIBLE_TYPE")
    @Throws(RowsExceededException::class, WriteException::class)
    fun writeCell(
        columnPosition: Int, rowPosition: Int, contents: String, headerCell: Boolean,
        sheet: WritableSheet
    ) {
        //create a new cell with contents at position
        val newCell = Label(columnPosition, rowPosition, contents)

        if (headerCell) {
            //give header cells size 10 Arial bolded
            val headerFont = WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)
            val headerFormat = WritableCellFormat(headerFont)
            //center align the cells' contents
            headerFormat.alignment = Alignment.CENTRE
            newCell.cellFormat = headerFormat
        }

        sheet.addCell(newCell)
    }
}