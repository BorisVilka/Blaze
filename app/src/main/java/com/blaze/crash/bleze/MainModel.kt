package com.blaze.crash.bleze

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainModel(context: Context) : ViewModel() {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    val balanceLiveData: MutableLiveData<Int> = MutableLiveData(prefs.getInt("Balance", 10000))

    init {
        balanceLiveData.observeForever { prefs.edit().putInt("Balance", it).apply() }
    }


    var balance
        get() = balanceLiveData.value!!
        set(value) {
            balanceLiveData.value = value
        }


}