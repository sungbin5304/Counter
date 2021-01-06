package me.sungbin.counter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    val names = MutableLiveData<Counter>() // 이름 중복할 수 있도록 uuid 사용
    val counts = HashMap<String, Int>() // uuid, count
}
    