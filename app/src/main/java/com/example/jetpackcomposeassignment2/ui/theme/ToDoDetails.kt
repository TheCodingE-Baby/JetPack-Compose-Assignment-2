package com.example.jetpackcomposeassignment2.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeassignment2.data.ToDoModel

@Composable
fun ToDoDetails(todo: ToDoModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Task #${todo.toDoId}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = todo.toDoName,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (todo.isDone) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = "Status",
                tint = if (todo.isDone) Color.Green else Color.Red
            )
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedVisibility(visible = todo.isDone) {
                Text("Done!", color = Color.Green
                )
            }
                Text(
                    text = if (todo.isDone) "Done!" else "Pending...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }