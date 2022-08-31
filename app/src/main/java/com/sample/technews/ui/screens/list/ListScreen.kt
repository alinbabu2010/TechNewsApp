package com.sample.technews.ui.screens.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.sample.technews.ui.theme.TechNewsTheme

@Composable
fun ListScreen(navController: NavHostController? = null) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

    }

}


@Preview(showBackground = true)
@Composable
private fun ListScreenPreview() {
    TechNewsTheme {
        ListScreen()
    }
}