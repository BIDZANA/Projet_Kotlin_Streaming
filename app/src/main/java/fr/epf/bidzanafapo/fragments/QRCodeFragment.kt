package fr.epf.bidzanafapo.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import fr.epf.bidzanafapo.MainActivity
import fr.epf.bidzanafapo.R
import fr.epf.bidzanafapo.ScanQrCodeActivity


class QRCodeFragment(private val context: MainActivity) : Fragment() {
/*
    private lateinit var qrCodeValueTextView : TextView
    private lateinit var startScanButton : Button
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val data = it.data?.getStringExtra(ScanQrCodeActivity.QR_CODE_KEY)
            updateQrCodeTextView(data)
        }
    }

    private fun updateQrCodeTextView(data: String?) {
        data?.let{
            context.runOnUiThread{
                qrCodeValueTextView.text = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qrcode, container, false)

        if (view != null) {
            qrCodeValueTextView = view.findViewById(R.id.text_scan_prompt)
        }
        if (view != null) {
            startScanButton = view.findViewById(R.id.btn_scan)
        }

        initButtonClickListener()

        return view
    }

    private fun initButtonClickListener() {
        startScanButton.setOnClickListener{
            val intent = Intent(context, ScanQrCodeActivity::class.java)
            resultLauncher.launch(intent)
        }
    }*/
}
