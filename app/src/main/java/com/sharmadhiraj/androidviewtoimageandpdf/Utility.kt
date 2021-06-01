package com.sharmadhiraj.androidviewtoimageandpdf

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Color.WHITE
import android.graphics.drawable.Drawable
import android.os.Environment
import android.view.View
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class Utility {

    companion object {

        fun getBitmapFromView(view: View): Bitmap? {
            if (view.background == null) view.setBackgroundColor(WHITE)
            val returnedBitmap = Bitmap.createBitmap(
                view.width,
                view.height,
                ARGB_8888
            )
            val canvas = Canvas(returnedBitmap)
            val bgDrawable: Drawable = view.background
            bgDrawable.draw(canvas)
            view.draw(canvas)
            return returnedBitmap
        }

        fun saveImage(bitmap: Bitmap) {
            try {
                val out = FileOutputStream(getFile(true))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun savePDF(bitmap: Bitmap) {
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(getFile(false)))
            document.open()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            try {
                val image = Image.getInstance(byteArray)
                val documentWidth: Float =
                    document.pageSize.width - document.leftMargin() - document.rightMargin()
                val documentHeight: Float =
                    document.pageSize.height - document.topMargin() - document.bottomMargin()
                image.scaleToFit(documentWidth, documentHeight)
                document.add(image)
            } catch (e: Exception) {
            }
            document.close()
        }

        private fun getFile(isImage: Boolean): File {
            val root: String = Environment.getExternalStorageDirectory().toString()
            val myDir = File("$root/AndroidViewToImageAndPDF")
            myDir.mkdirs()
            return File(myDir, "${UUID.randomUUID()}." + if (isImage) "jpg" else "pdf")
        }

    }

}

