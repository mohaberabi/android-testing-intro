package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository

class StatisticsViewModelFactory(
    private val taskRepository: TasksRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("ERROR")
    }


}
