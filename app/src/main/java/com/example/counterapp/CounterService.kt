package com.example.counterapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class CounterService : Service() {
    private val binder = CounterBinder()
    private var counterValue = 0

    inner class CounterBinder : Binder() {
        fun getService(): CounterService {
            return this@CounterService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun incrementValue() {
        counterValue++
    }

    fun decrementValue() {
        if (counterValue > 0) {
            counterValue--
        }
    }

    fun displayValue() {
        showToast("$counterValue")
    }

    fun currentValue(): Int {
        return counterValue
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}