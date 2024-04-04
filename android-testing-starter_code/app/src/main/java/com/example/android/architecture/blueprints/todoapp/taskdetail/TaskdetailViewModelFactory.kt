package com.example.android.architecture.blueprints.todoapp.taskdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository

class TaskdetailViewModelFactory(private val taskRepository: TasksRepository) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            return TaskDetailViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("ERROR")
    }
}