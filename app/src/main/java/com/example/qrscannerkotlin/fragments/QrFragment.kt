package com.example.qrscannerkotlin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.example.qrscannerkotlin.Communicator
import com.example.qrscannerkotlin.MainActivity
import com.example.qrscannerkotlin.R
import kotlin.reflect.typeOf


class QrFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var scannerView: CodeScannerView
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onBackListener()
        return inflater.inflate(R.layout.fragment_q_r, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    companion object {
        @JvmStatic
        fun newInstance() = QrFragment()
    }

    private fun init(view : View){
        scannerView = view.findViewById(R.id.scanner_view)
        communicator = activity as Communicator
        codeScanner(view)
    }

    private fun codeScanner(view : View){

        codeScanner = CodeScanner(view.context, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                checkAndDisplayQrResultData(it.toString())
            }

            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    private fun checkAndDisplayQrResultData(it : String){
        if(it.contains("File")){
            communicator.passData(it)
            println(it)
        } else {
            writeNotification(it)
        }
    }

    private fun writeNotification(it : String){
        activity?.runOnUiThread {
            Toast.makeText(activity, "This QR or barcode is not supported by this app", Toast.LENGTH_LONG).show()
            Toast.makeText(activity, "It contains: $it", Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, "Press on the screen to continue scanning", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun onBackListener(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as MainActivity).setFragment(MainFragment.newInstance())
                }
            })
    }


}