package me.sungbin.counter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.androidutils.extensions.join
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import me.sungbin.counter.databinding.ActivityMainBinding
import me.sungbin.counter.databinding.LayoutDialogAddCounterBinding
import me.sungbin.counter.databinding.LayoutDialogRandomChooseBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val vm: CounterViewModel by viewModels()
    private val items = ArrayList<Counter>()
    private val adapter by lazy { CounterAdapter(items, vm) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvCounter.adapter = adapter

        vm.names.observe(this) {
            items.add(it)
            adapter.notifyDataSetChanged()
        }

        binding.fabTools.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle(getString(R.string.main_tool))
            dialog.setItems(arrayOf("랜덤 숫자 뽑기", "[추후 더 추가됩니다]")) { _, index ->
                when (index) {
                    0 -> {
                        showRandomGameDialog()
                    }
                }
            }
            dialog.show()
        }

        binding.fabAdd.setOnClickListener {
            val layout = LayoutDialogAddCounterBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this)
            dialog.setView(layout.root)
            dialog.setPositiveButton(getString(R.string.create)) { _, _ ->
                vm.names.postValue(
                    Counter(
                        layout.etCounterName.text.toString(),
                        UUID.randomUUID().toString()
                    )
                )
            }
            dialog.show()
        }

    }

    private fun showRandomGameDialog() {
        val layout = LayoutDialogRandomChooseBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.main_random_choose))
        dialog.setView(layout.root)
        dialog.setPositiveButton(getString(R.string.create)) { _, _ ->
            val maxIndex = layout.etMaxIndex.text.toString()
            val chooseCount = layout.etChooseCount.text.toString()
            if (maxIndex.isBlank() || chooseCount.isBlank()) {
                ToastUtil.show(
                    applicationContext,
                    getString(R.string.dialog_confirm_input_all),
                    ToastLength.SHORT,
                    ToastType.WARNING
                )
            } else {
                val chosenIndex = ArrayList<Int>()
                while (true) {
                    if (chosenIndex.size == chooseCount.toInt()) {
                        break
                    }
                    val randomIndex = Random.nextInt(maxIndex.toInt() + 1)
                    if (!chosenIndex.contains(randomIndex)) {
                        chosenIndex.add(randomIndex)
                    }
                }
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_chosen_result_title))
                    .setMessage(getString(R.string.dialog_chosen_result, chosenIndex.join(", ")))
                    .show()
            }
        }
        dialog.show()
    }

}