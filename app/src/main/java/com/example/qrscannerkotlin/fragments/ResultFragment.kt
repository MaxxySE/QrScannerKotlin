package com.example.qrscannerkotlin.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.qrscannerkotlin.MainActivity
import com.example.qrscannerkotlin.R
import java.io.InputStream


class ResultFragment : Fragment() {

    private var result: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        result = arguments?.getString("result")
        splitText(view)
        clickListener(view)
        return view
    }

    fun clickListener(view : View){
        view.findViewById<Button>(R.id.rescanBtn).setOnClickListener {
            (activity as MainActivity).setFragment(QrFragment.newInstance())
        }

        view.findViewById<Button>(R.id.backBtn).setOnClickListener {
            (activity as MainActivity).setFragment(MainFragment.newInstance())
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as MainActivity).setFragment(MainFragment.newInstance())
                }
            })
    }

    private fun splitText(view : View){
        //split делит элементы по запятой и пробелу, т.к. КУары которые я сделал,
        // в них названия делятся через запятую с пробелом, нужно будет изменить
        val strings = result?.split(", ")?.toTypedArray()
        setImage(view, strings?.get(0).toString())
        setText(view, strings?.get(1).toString())
    }


    private fun setImage(view : View, imageFile : String){
        val inputStream : InputStream? = activity?.assets?.open("$imageFile.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        view.findViewById<ImageView>(R.id.resultImage).setImageBitmap(bitmap)
    }

    private fun setText(view : View, textFile : String){
        val text: String = activity?.assets?.open("$textFile.txt")?.bufferedReader().use {
            it?.readText().toString() }
        view.findViewById<TextView>(R.id.resultText).text = text
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultFragment()
    }
}