package com.mobolajia.notesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mobolajia.notesapp.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginTests {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun app_opens_on_login_screen(){
        onView(withId(R.id.login_txt)).check(matches(isDisplayed()))
        onView(withId(R.id.email_lyt)).check(matches(isDisplayed()))
        onView(withId(R.id.password_ly)).check(matches(isDisplayed()))
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_forgot_password_opens_forgot_password() {
        onView(withId(R.id.forgot_password)).perform(click())
        onView(withId(R.id.forgot_password_txt)).check(matches(isDisplayed()))
        onView(withId(R.id.reset_password_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_text_opens_register_screen() {
        onView(withId(R.id.register_txt)).perform(click())
        onView(withId(R.id.first_name_lyt)).check(matches(isDisplayed()))
        onView(withId(R.id.register_btn)).check(matches(isDisplayed()))
    }
}
