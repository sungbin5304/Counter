package me.sungbin.counter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import me.sungbin.counter.databinding.ActivityMainBinding
import me.sungbin.counter.databinding.LayoutDialogAddCounterBinding

class MainActivity : AppCompatActivity() {

    private val vm: CounterViewModel by viewModels()
    private val items = ArrayList<String>()
    private val adapter by lazy { CounterAdapter(items) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvCounter.adapter = adapter

        vm.counts.observe(this) {
            items.add(it)
            adapter.notifyDataSetChanged()
        }

        binding.fabAdd.setOnClickListener {
            val layout = LayoutDialogAddCounterBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this)
            dialog.setView(layout.root)
            dialog.setPositiveButton(getString(R.string.create)) { _, _ ->
                vm.counts.value = layout.etCounterName.text.toString()
            }
            dialog.show()
        }

    }
}