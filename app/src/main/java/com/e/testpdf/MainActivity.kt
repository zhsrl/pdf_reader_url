package com.e.testpdf

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil

class MainActivity : AppCompatActivity(), DownloadFile.Listener {

    lateinit var root: LinearLayout
    lateinit var download: MaterialButton
    lateinit var remotePdf: RemotePDFViewPager
    lateinit var adapter: PDFPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = findViewById(R.id.root)
        download = findViewById(R.id.downloadBTN)

        setDownloadListener()

    }

    fun getUrl(): String{
        return "https://firebasestorage.googleapis.com/v0/b/booker-5480d.appspot.com/o/Uploads%2FPDFBooks%2FLiterature%2FAntract%2FAntract.pdf?alt=media&token=3e2e0a37-f383-4728-8245-58e7c3f581c2"
    }

    fun setDownloadListener(){
        val listener: DownloadFile.Listener = this

        download.setOnClickListener {
            remotePdf = RemotePDFViewPager(this, getUrl(), listener)
            remotePdf.id = R.id.pdfViewPager
        }
    }

    fun updateLayout() {
        root.removeAllViewsInLayout()
        root.addView(
            download,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        root.addView(
            remotePdf,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePdf.adapter = adapter
        updateLayout()
    }

    override fun onFailure(e: Exception?) {
        e?.printStackTrace()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
    }
}