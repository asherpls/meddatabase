package com.example.meddatabase.screens

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.meddatabase.R
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder( MethodSorters.DEFAULT)
class SignUpScreenTests : ScreenTests() {

    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `check default state of the sign up screen`() {
        rule.onNode(signUpButton).performClick()
        //on sign up page
        val pageTitle = hasText(rule.activity.getString(R.string.sign_up_screen_title))
        rule.onNode(pageTitle).assertExists()
        rule.onNode(bottomNavBar).assertDoesNotExist()
        rule.onNode(emailAddressTextField).assertExists()
        rule.onNode(passwordTextField).assertExists()
        rule.onNode(submitButton).assertExists()
    }

    @Test
    fun `enter valid sign up details`(){
        rule.onNode(signUpButton).performClick()

        rule.onNode(emailAddressTextField).performTextInput(VALIDUSERNAME)
        rule.onNode(passwordTextField).performTextInput(VALIDPASSWORD)
        rule.onNode(submitButton).performClick()
    }

    //@Test
    fun `enter invalid sign up details`(){
        rule.onNode(signUpButton).performClick()

        rule.onNode(emailAddressTextField).performTextInput("invalid username")
        rule.onNode(passwordTextField).performTextInput("invalid password")
        rule.onNode(submitButton).performClick()
    }
}