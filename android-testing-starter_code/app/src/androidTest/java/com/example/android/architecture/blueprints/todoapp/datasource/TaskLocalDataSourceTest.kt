package com.example.android.architecture.blueprints.todoapp.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest

class TaskLocalDataSourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: TasksLocalDataSource
    private lateinit var database: ToDoDatabase


    @Before

    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        localDataSource = TasksLocalDataSource(
            database.taskDao(),
            Dispatchers.Main
        )
    }

    @After

    fun tearDown() = database.close()


    @Test
    fun getTask() {


        val newTask = Task("ASDsa", "ASDSAd")
        
        runBlockingTest {
            val result = localDataSource.getTask(newTask.id)
            assertThat(result.succeeded, `is`(true))
            result as Result.Success
            assertThat(result.data.title, `is`("title"))
            assertThat(result.data.description, `is`("description"))
            assertThat(result.data.isCompleted, `is`(false))

        }


    }
}