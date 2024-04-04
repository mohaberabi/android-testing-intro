package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

class FakeTaskRepository : TasksRepository {


    private var shouldReturnError: Boolean = false
    private var tasksMap: LinkedHashMap<String, Task> = LinkedHashMap()

    private val observableTasks = MutableLiveData<Result<List<Task>>>()
    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {

        return if (shouldReturnError) {
            Result.Error(Exception("ERROR"))
        } else {
            Result.Success(tasksMap.values.toList())

        }
    }


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {

        runBlocking { refreshTasks() }
        return observableTasks
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    fun addTasks(vararg tasks: Task) {

        for (task in tasks) {
            tasksMap[task.id] = task
        }
        runBlocking {
            refreshTasks()
        }
    }
}