package fr.epf.bidzanafapo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class QRCodeActivity : AppCompatActivity() {

    private lateinit var qrCodeValueTextView: TextView
    private lateinit var startScanButton: Button
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data?.getStringExtra(ScanQrCodeActivity.QR_CODE_KEY)
                updateQrCodeTextView(data)
            }
        }

    private fun updateQrCodeTextView(data: String?) {
        data?.let {
            this.runOnUiThread {
                qrCodeValueTextView.text = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        qrCodeValueTextView = findViewById(R.id.text_scan_prompt)
        startScanButton = findViewById(R.id.btn_scan)

        initButtonClickListener()

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigation_bar_view)
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.scanner_page -> {
                    this.startActivity(Intent(this, QRCodeActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.home_page -> {
                    this.startActivity(Intent(this, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.favorite_page -> {
                    this.startActivity(Intent(this, FavoriteActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }

    private fun initButtonClickListener() {
        startScanButton.setOnClickListener {
            val intent = Intent(this, ScanQrCodeActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}