package com.example.gridview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class MyFragement: DialogFragment() {

    private var mListener: OnCompleteListener? = null

    // make sure the Activity implemented it

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            this.mListener = context as OnCompleteListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnCompleteListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View=inflater.inflate(R.layout.alertlayout,container,false)
        val okbt=rootView.findViewById<Button>(R.id.okbt)
        val cancelbt=rootView.findViewById<Button>(R.id.cancelbt)
        val alname=rootView.findViewById<EditText>(R.id.an_edit)

        okbt.setOnClickListener {
            var txt=alname.text.toString()
            if(txt=="") {
                dismiss()
            }else {
                mListener?.onComplete(txt)
                dismiss()
            }
        }
        cancelbt.setOnClickListener {
            dismiss()
        }
        return rootView
    }
    interface OnCompleteListener {
        fun onComplete(time: String?)
    }

}