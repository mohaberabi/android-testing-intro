package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

class FakeDataSource(
    private var tasks:
    MutableList<Task> = mutableListOf()
) : TasksDataSource {
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Result<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        tasks.add(task)
    }

    override suspend fun completeTask(task: Task) {
    }

    override suspend fun completeTask(taskId: String) {
    }

    override suspend fun activateTask(task: Task) {
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        tasks = tasks.filter {
            !it.isCompleted
        }.toMutableList()
    }

    override suspend fun deleteAllTasks() {
        tasks = emptyList<Task>().toMutableList()
    }

    override suspend fun deleteTask(taskId: String) {
        tasks.remove(tasks.first {
            it.id == taskId
        })
    }
}