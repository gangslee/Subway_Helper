package com.example.subwayhelper.activity

import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.subwayhelper.R
import com.example.subwayhelper.SubwayHelperApplication

class SetSpinner(spinner: Spinner, lineData: Int) {

    private val spinner: Spinner
    private var Adapter: ArrayAdapter<CharSequence>

    init {

        this.spinner = spinner
        Adapter = ArrayAdapter.createFromResource(
            SubwayHelperApplication.applicationContext(),
            lineData, android.R.layout.simple_spinner_item
        )
    }

    fun drawSpinner(bool:Boolean) {

        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(Adapter)
        spinner.setEnabled(bool)

    }


}