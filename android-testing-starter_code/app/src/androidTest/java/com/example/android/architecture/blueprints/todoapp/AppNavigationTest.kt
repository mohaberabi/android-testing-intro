package com.example.android.architecture.blueprints.todoapp

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.example.android.architecture.blueprints.todoapp.util.DataBindingIdleResource
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdleResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppNavigationTest {


    private lateinit var tasksRepository: TasksRepository
    private val dataBindingIdlingResource = DataBindingIdleResource()

    @Before
    fun init() {
        tasksRepository = ServiceLocator.provideTaskRepository(getApplciationContext())

    }

    @Before
    fun initDataBdiningResources() {
        IdlingRegistry.getInstance().register(EspressoIdleResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

    }

    @After
    fun tearDownRepository() {
        ServiceLocator.tearDown()
    }

    @After
    fun tearDownIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdleResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)

    }

    @Test

    fun clickOnDrawerIConOpensNavigation() {
        ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        activityScenario.close()

    }

    @Test
    fun taskDetailScreen_doubleUpButton() = runBlocking {
        val task = Task("Up button", "Description")
        tasksRepository.saveTask(task)

        // Start the Tasks screen.
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // 1. Click on the task on the list.

        // 2. Click on the edit task button.

        // 3. Confirm that if we click Up button once, we end up back at the task details page.

        // 4. Confirm that if we click Up button a second time, we end up back at the home screen.

        // When using ActivityScenario.launch(), always call close().
        activityScenario.close().
    }


    @Test
    fun taskDetailScreen_doubleBackButton() = runBlocking {
        val task = Task("Back button", "Description")
        tasksRepository.saveTask(task)

        // Start Tasks screen.
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // 1. Click on the task on the list.

        // 2. Click on the Edit task button.

        // 3. Confirm that if we click Back once, we end up back at the task details page.

        // 4. Confirm that if we click Back a second time, we end up back at the home screen.

        // When using ActivityScenario.launch(), always call close()
        activityScenario.close()
    }
}
fun <T : Activity> ActivityScenario<T>.getToolbarNavigationContentDescription() : String { var description = "" onActivity { description = it.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String } return description }