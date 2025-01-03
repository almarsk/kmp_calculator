package org.almarsk

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.currentKoinScope

expect class KoinInitializer {
    fun init()
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}