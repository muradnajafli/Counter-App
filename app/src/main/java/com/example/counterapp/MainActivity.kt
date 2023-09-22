package com.example.counterapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.counterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var counterService: CounterService? = null
    private var isBound = false
    private lateinit var binding: ActivityMainBinding


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CounterService.CounterBinder
            counterService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            counterService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.incrementButton.setOnClickListener {
            counterService?.incrementValue()
            updateCounterTextView()

        }
        binding.decrementButton.setOnClickListener {
            counterService?.decrementValue()
            updateCounterTextView()
        }
        binding.presentValueButton.setOnClickListener {
            counterService?.displayValue()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, CounterService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private fun updateCounterTextView() {
        val currentValue = counterService?.currentValue() ?: 0
        binding.textView.text = "$currentValue"
    }
}