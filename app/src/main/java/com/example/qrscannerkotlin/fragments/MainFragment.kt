package com.example.qrscannerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.qrscannerkotlin.MainActivity
import com.example.qrscannerkotlin.R


class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        startScanListener(view)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private fun startScanListener(view : View){
        view.findViewById<Button>(R.id.startScanBtn).setOnClickListener {
            (activity as MainActivity).setFragment(QrFragment.newInstance())
        }
    }

}