package org.almarsk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private var _args = MutableStateFlow("")
    val args = _args.asStateFlow()

    private var _justCalculated = MutableStateFlow(false)

    fun addArg(c: Char) {

        if (_args.value.last().digitToIntOrNull() != null && c.digitToIntOrNull() == null)
            return

        if (_justCalculated.value && c.digitToIntOrNull() != null) {
            clear()
            _justCalculated.update { false }
        }

        viewModelScope.launch {
            _args.update { it + c }
        }
    }

    fun erase() {
        viewModelScope.launch {
            _args.update { it.dropLast(1) }
        }
    }

    fun clear() {
        viewModelScope.launch {
            _args.update { "" }
        }
    }

    fun calculate() {
        viewModelScope.launch {
            try {
                val res = ArithmeticManager().evaluate(_args.value).toString()

                val parts = res.split(".")
                var dec = ""

                if (parts.size > 1) {
                    dec = parts[1].substring(0, minOf(3, parts[1].length))
                }

                _args.update { parts[0] + "." + dec }
                _justCalculated.update { true }
            } catch (e: Exception) {
                println("Invalid calculator operation: $e")
            }
        }
    }

}