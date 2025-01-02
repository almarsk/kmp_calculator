package org.almarsk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun Calculator() {
    val viewModel = koinViewModel<CalculatorViewModel>()
    val args by viewModel.args.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(args) {
        coroutineScope.launch {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .heightIn(200.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    fontSize = 80.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.End,
                    text = args
                )
            }

            Column(modifier = Modifier.fillMaxSize()) {
                for (row in calculatorArgs) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(10.dp),

                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (button in row) {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                                    //.weight(1f)
                                    .clickable {
                                        when (button) {
                                            is CalculatorOperation.Add -> viewModel.addArg(button.arg)
                                            CalculatorOperation.Calculate -> viewModel.calculate()
                                            CalculatorOperation.Erase -> viewModel.erase()
                                            CalculatorOperation.Clear -> viewModel.clear()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = button.displayName,
                                    fontSize = 50.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}