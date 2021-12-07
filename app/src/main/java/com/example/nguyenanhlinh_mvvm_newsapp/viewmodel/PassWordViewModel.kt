package com.example.nguyenanhlinh_mvvm_newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PassWordViewModel(app: Application) : AndroidViewModel(app) {
    var passCode: MutableLiveData<String> = MutableLiveData()
    var check: MutableLiveData<Int?> = MutableLiveData()
    var passWord = ""
    var pass = ""
    var isCheckStartActivity = false
    private val sharedPreferences: SharedPreferences =
        app.getSharedPreferences("SHARED", Context.MODE_PRIVATE)

    init {
        passWord = sharedPreferences.getString("pass", "")!!
    }

    fun checkPassCode(number: Int) {
        if (number == 11 && pass.isNotEmpty()) {
            pass = pass.substring(0, pass.length - 1)
        } else if (number != 11) {
            pass += number.toString()
        }
        setPassCode()
    }

    private fun setPassCode() {
        passCode.value = pass
        if (passWord == "") {
            if (pass == "") {
                check.value = 0
            } else {
                sharedPreferences.edit().putString("pass", pass).apply()
                check.value = if (isCheckStartActivity) 3 else 2
            }


        } else {
            if (pass != passWord) {
                check.value = 1
            } else {
                check.value = if (isCheckStartActivity) 3 else 2
            }
        }
    }
}
