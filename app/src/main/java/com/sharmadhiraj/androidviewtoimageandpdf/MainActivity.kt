package com.sharmadhiraj.androidviewtoimageandpdf

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.sharmadhiraj.androidviewtoimageandpdf.Utility.Companion.getBitmapFromView
import com.sharmadhiraj.androidviewtoimageandpdf.Utility.Companion.savePDF
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export_as_image -> {
                Utility.saveImage(getBitmapFromView(container)!!)
                Toast.makeText(this, "Image successfully saved.", LENGTH_SHORT).show()
                true
            }
            R.id.action_export_as_pdf -> {

                val inflater =
                    this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val root = inflater.inflate(R.layout.activity_main, null)
                root.isDrawingCacheEnabled = true
                val screen = getBitmapFromView(
                    this.window.findViewById(R.id.container)
                )
                savePDF(screen!!)
//                savePDF(getBitmapFromView(container)!!)
                Toast.makeText(this, "PDF successfully saved.", LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}