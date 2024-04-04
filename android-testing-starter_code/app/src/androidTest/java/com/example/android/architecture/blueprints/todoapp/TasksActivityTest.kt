package com.example.android.architecture.blueprints.todoapp

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity
import com.example.android.architecture.blueprints.todoapp.util.DataBindingIdleResource
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdleResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.not


@RunWith(AndroidJUnit4::class)

@LargeTest

class TasksActivityTest {
    private val dataBindingIdleResource = DataBindingIdleResource()

    private lateinit var repository: TasksRepository


    @Before
    fun init() {
        repository = ServiceLocator.provideTaskRepository(getApplicationContext())


        // this is not a test it's real data
        runBlocking {

            repository.deleteAllTasks()
        }
    }


    // we are now registering the databidning resoruces and the long time methods used by the app
    // this will make sure that espresso waits while executing any tests
    //to avoid any flecky tests
    @Before
    fun setupIdlingResource() {


        // wait if the method or operation is still not done yet
        IdlingRegistry.getInstance().register(EspressoIdleResource.countingIdlingResource)

        // wait if there is data binding is still waiting
        IdlingRegistry.getInstance().register(dataBindingIdleResource)


    }


    // we are now Un registering the databidning resoruces and the long time methods used by the app

    @After
    fun tearDowndIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdleResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdleResource)

    }

    @Test

    fun editTask() = runBlocking {


        repository.saveTask(Task("", ""))
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)


        // wait for the databinding to be done
        // why we are not using the espressoidlig resource ? because in each method we used the global function
        // which will hold the counter when method is still exectuing [EspressoIdleResource] you wrote it yourself
        dataBindingIdleResource.monitorActivity(activityScenario)

        // Click on the task on the list and verify that all the data is correct.
        onView(withText("TITLE1")).perform(click())
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("TITLE1")))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("DESCRIPTION")))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))

        // Click on the edit button, edit, and save.
        onView(withId(R.id.edit_task_fab)).perform(click())
        onView(withId(R.id.add_task_title_edit_text)).perform(replaceText("NEW TITLE"))
        onView(withId(R.id.add_task_description_edit_text)).perform(replaceText("NEW DESCRIPTION"))
        onView(withId(R.id.save_task_fab)).perform(click())

        // Verify task is displayed on screen in the task list.
        onView(withText("NEW TITLE")).check(matches(isDisplayed()))
        // Verify previous task is not displayed.
        onView(withText("TITLE1")).check(doesNotExist())

        activityScenario.close()
    }

    @After
    fun tearDown() = ServiceLocator.tearDown()
}