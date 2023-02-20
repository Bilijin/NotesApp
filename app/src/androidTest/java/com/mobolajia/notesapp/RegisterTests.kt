package com.mobolajia.notesapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.mobolajia.notesapp.ui.fragment.RegisterFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegisterTests {

    @Test
    fun testNavController() {
        launchFragmentInContainer<RegisterFragment>(themeResId = R.style.Theme_NotesApp)
        onView(withId(R.id.register_txt)).check(matches(isDisplayed()))
    }
}