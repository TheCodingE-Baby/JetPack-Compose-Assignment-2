package com.example.jetpackcomposeassignment2.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ToDoDetails(toDoId: Int){
    Column(modifier = Modifier.padding(16.dp)) {
        Text("ToDo ID: $toDoId", style = MaterialTheme.typography.headlineSmall)
    }
}