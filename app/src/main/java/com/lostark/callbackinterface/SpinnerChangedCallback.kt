package com.lostark.callbackinterface

interface SpinnerChangedCallback {
    fun onEngravingSpinnerChanged(name: String, value: String)
    fun onPlusMinusSpinnerChanged(name: String, value: String)
}