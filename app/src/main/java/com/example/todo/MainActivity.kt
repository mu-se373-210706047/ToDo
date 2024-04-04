package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.ToDoTheme
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoList()
                }
            }
        }
    }
}

@Composable
fun ToDoList() {
    var newTask by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }
    val taskCompleted = remember { mutableStateListOf<Boolean>() }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Text input field
        OutlinedTextField(
            value = newTask,
            onValueChange = { newTask = it },
            label = { Text("Enter task") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add button
        Button(
            onClick = {
                if (newTask.isNotBlank()) {
                    tasks.add(newTask)
                    taskCompleted.add(false) // Initialize task as not completed
                    newTask = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display tasks
        tasks.forEachIndexed { index, task ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = taskCompleted[index],
                    onCheckedChange = { isChecked -> taskCompleted[index] = isChecked }
                )
                Text(text = task, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        tasks.removeAt(index)
                        taskCompleted.removeAt(index)
                    }, // Remove the task when clicked
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // Use your delete icon
                        contentDescription = "Delete task",
                        tint = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToDoList() {
    ToDoTheme {
        ToDoList()
    }
}
