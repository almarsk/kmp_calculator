package org.almarsk

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {

    private var _args = MutableStateFlow("")
    val args = _args.asStateFlow()

    fun addArg(c: Char){
        _args.update { it + c }
    }

    fun erase(){
        _args.update { it.dropLast(1) }
    }

    fun clear(){
        _args.update { "" }
    }

}