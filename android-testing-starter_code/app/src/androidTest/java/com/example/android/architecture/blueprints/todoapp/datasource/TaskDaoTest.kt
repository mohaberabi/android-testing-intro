package com.example.android.architecture.blueprints.todoapp.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.bytebuddy.matcher.ElementMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest

class TaskDaoTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase


    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).build()
    }


    @Test
    fun insertTaskAndGetById() = runBlockingTest {

        val task = Task("T", "D")
        database.taskDao().insertTask(task)
        val loaded = database.taskDao().getTaskById(task.id)

        assertThat<Task>(loaded as Task, notNullValue())
        assertThat<Task>(loaded.id, `is`(task.id))
        assertThat<Task>(loaded.title, `is`(task.title))
        assertThat<Task>(loaded.description, `is`(task.description))

    }

    @After
    fun closeDb() = database.close()

}