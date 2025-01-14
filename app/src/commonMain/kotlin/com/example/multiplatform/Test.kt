package com.example.multiplatform

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import multiplatform.app.generated.resources.Res
import multiplatform.app.generated.resources.test
import org.jetbrains.compose.resources.stringResource

@Composable
fun Test() {
    Column {
        HelloWorld()
        Text(stringResource(Res.string.test))
    }
}