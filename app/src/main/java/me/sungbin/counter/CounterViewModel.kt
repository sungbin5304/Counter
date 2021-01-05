package me.sungbin.counter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    val counts = MutableLiveData<String>()
}
    