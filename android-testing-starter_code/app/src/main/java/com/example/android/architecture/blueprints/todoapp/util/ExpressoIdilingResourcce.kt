package com.example.android.architecture.blueprints.todoapp.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdleResource {

    private const val RESOURCE = "GLOBAL"


    // when counter of hte resource greater than zero , app is considered working
    // when less than zero so the app is idle now
    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)


    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {

        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}


inline fun <T> wrapEspressoResource(
    function: () -> T
): T {
    EspressoIdleResource.increment()
    return try {
        function()
    } finally {
        EspressoIdleResource.decrement()
    }
}