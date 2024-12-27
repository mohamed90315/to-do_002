package com.example.to_doapp

data class Task(
    val name: String,
    val details: String,
    var isCompleted: Boolean = false // Default value set for convenience
)
