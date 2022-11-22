package com.abanoub.currencyconverter.ui.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abanoub.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConvert.setOnClickListener {
            val amount = binding.amount.editText!!.text.toString()
            val fromCurrency = binding.spinnerFromCurrency.selectedItem.toString()
            val toCurrency = binding.spinnerToCurrency.selectedItem.toString()

            viewModel.convert(
                amount,
                fromCurrency,
                toCurrency
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progress.visibility = View.GONE
                        binding.result.apply {
                            setTextColor(Color.BLACK)
                            text = event.result
                        }
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progress.visibility = View.GONE
                        binding.result.apply {
                            setTextColor(Color.RED)
                            text = event.error
                        }
                    }
                    is MainViewModel.CurrencyEvent.Empty -> {
                        // Nothing
                    }
                }
            }
        }

    }
}