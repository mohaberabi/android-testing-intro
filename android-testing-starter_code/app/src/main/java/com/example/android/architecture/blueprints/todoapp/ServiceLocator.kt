package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()

    private var database: ToDoDatabase? = null


    private fun createDatabase(context: Context): ToDoDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Todo.db"

        ).build()

        database = result
        return result


    }

    @Volatile
    var tasksRepository: TasksRepository? = null
        @VisibleForTesting set

    fun provideTaskRepository(context: Context): TasksRepository {

        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)

        }
    }

    private fun createTasksRepository(context: Context): TasksRepository {

        val newRepo =
            DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): TasksDataSource {

        val database = database ?: createDatabase(context)
        return TasksLocalDataSource(database.taskDao())

    }

    @VisibleForTesting
    fun tearDown() {
        synchronized(lock = lock) {
            runBlocking {
                TasksRemoteDataSource.deleteAllTasks()

            }
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            tasksRepository = null
        }
    }

}