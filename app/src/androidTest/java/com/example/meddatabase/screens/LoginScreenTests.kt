package com.example.meddatabase.screens

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.meddatabase.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder( MethodSorters.DEFAULT)
@RunWith(AndroidJUnit4::class)
open class LoginScreenTests : ScreenTests(){

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `check state of the login in page`() {
        rule.onNode(submitButton).assertExists()
        rule.onNode(forgotPasswordButton).assertExists()
        rule.onNode(signUpButton).assertExists()

        rule.onNode(emailAddressTextField).assertExists()
        rule.onNode(passwordTextField).assertExists()
    }

    @Test
    fun `check if user can sign in and proceed to the home page`(){
        `sign in`()
        rule.onNode(bottomNavBar).assertExists()
    }

    @Test
    fun `attempt sign in with invalid details`(){
        `sign in`(email = "invalid email",password="invalid password")
        `check state of the login in page`()
    }

    @Test
    fun `move to the sign up page`(){
        rule.onNode(signUpButton).performClick()
        val pageTitle = hasText(rule.activity.getString(R.string.sign_up_screen_title))
        rule.onNode(pageTitle).assertExists()
    }

    //todo
    @Test
    fun `valid request for forgot password with email`(){
        `enter email`()
        rule.onNode(forgotPasswordButton).performClick()
        `check state of the login in page`()
    }

    //todo
    @Test
    fun `invalid request for forgot password`(){
        `enter email`(email = "invalid email")
        rule.onNode(forgotPasswordButton).performClick()
        `check state of the login in page`()

    }
}