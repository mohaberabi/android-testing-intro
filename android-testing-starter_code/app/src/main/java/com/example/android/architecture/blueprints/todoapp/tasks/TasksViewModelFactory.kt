package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository

class TasksViewModelFactory(private val tasksRepository: TasksRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(tasksRepository) as T
        }
        throw IllegalArgumentException("ERROR")
    }
}