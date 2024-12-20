package com.example.multiplatform

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import multiplatform.app.generated.resources.Res
import multiplatform.app.generated.resources.test
import org.jetbrains.compose.resources.stringResource

@Composable
fun Test() {
    Text(stringResource(Res.string.test))
}