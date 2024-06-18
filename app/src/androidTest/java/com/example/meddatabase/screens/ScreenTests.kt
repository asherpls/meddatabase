package com.example.meddatabase.screens

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.meddatabase.R
import com.example.meddatabase.core.MainActivity
import com.example.meddatabase.presentation.navigation.NavScreen
import org.junit.Before
import org.junit.Rule

open abstract class ScreenTests {
    @get:Rule
    var rule = createAndroidComposeRule<MainActivity>()

    //Nav bar items
    val bottomNavBar = hasContentDescription("bottom_nav")
    val exitNavBarItem = hasText(NavScreen.Exit.route)
    val homeNavBarItem = hasText(NavScreen.Home.route) and hasAnySibling(exitNavBarItem)
    val searchNavBarItem = hasText(NavScreen.Stats.route) and hasAnySibling(exitNavBarItem)

    lateinit var submitButton : SemanticsMatcher

    //Data for add screen
    val MEDNAME = "first1"
    val DETAILS = "surname1"
    val FORMATTEDDATE = "N/A"
    val EDITNAME = "tester1"

    val VALIDUSERNAME = "ashton@bt.com"
    val VALIDPASSWORD = "asher911"

    lateinit var medInfoTextField: SemanticsMatcher
    lateinit var detailsTextField: SemanticsMatcher
    lateinit var addScreenText : SemanticsMatcher
    lateinit var addButton : SemanticsMatcher
    lateinit var editButton : SemanticsMatcher
    lateinit var dateButton : SemanticsMatcher

    //For home screen
    val listItem = hasText("$MEDNAME [Exp: $FORMATTEDDATE]")
    val editedListItem = hasText("$EDITNAME$MEDNAME [Exp: $FORMATTEDDATE]")
    val fullListItem = hasText("$MEDNAME $DETAILS [Exp: $FORMATTEDDATE]")

    lateinit var homeScreenText : SemanticsMatcher
    lateinit var forgotPasswordButton : SemanticsMatcher
    lateinit var signUpButton : SemanticsMatcher
    lateinit var deleteButton: SemanticsMatcher

    //For login + sign up screen
    lateinit var emailAddressTextField : SemanticsMatcher
    lateinit var passwordTextField : SemanticsMatcher
    lateinit var backButton: SemanticsMatcher

    //For edit screen
    lateinit var editScreenText : SemanticsMatcher
    lateinit var searchScreenText : SemanticsMatcher


    @Before
    open fun setUp() {
        val BUTTON_POSTFIX = " button"
        forgotPasswordButton = hasContentDescription( rule.activity.getString(R.string.forgot_password) + BUTTON_POSTFIX)
        submitButton = hasContentDescription(rule.activity.getString(R.string.submit_button)+ BUTTON_POSTFIX)
        signUpButton = hasContentDescription(rule.activity.getString(R.string.sign_up_button) + BUTTON_POSTFIX)
        backButton = hasContentDescription(rule.activity.getString(R.string.back_button) + BUTTON_POSTFIX)
        deleteButton = hasContentDescription(rule.activity.getString(R.string.delete) + BUTTON_POSTFIX)
        addButton = hasContentDescription(rule.activity.getString(R.string.add) + BUTTON_POSTFIX)
        editButton = hasContentDescription(rule.activity.getString(R.string.edit) + BUTTON_POSTFIX)
        dateButton = hasContentDescription(rule.activity.getString(R.string.date_button_hint))

        emailAddressTextField = hasContentDescription(rule.activity.getString(R.string.email))
        passwordTextField = hasContentDescription(rule.activity.getString(R.string.password))

        medInfoTextField = hasContentDescription(rule.activity.getString(R.string.medication_hint))
        detailsTextField = hasContentDescription(rule.activity.getString(R.string.details_hint))

        homeScreenText = hasText(rule.activity.getString(R.string.home))
        addScreenText = hasText(rule.activity.getString(R.string.add)) and hasNoClickAction()
        editScreenText = hasText(rule.activity.getString(R.string.edit)) and hasNoClickAction()
        searchScreenText = hasText(rule.activity.getString(R.string.stats))
    }

    //Use for valid and invalid sign ins - use default values for generic log in
    fun `sign in`(email:String = VALIDUSERNAME, password: String = VALIDPASSWORD) {
        //rule.onNode(emailAddressTextField).printToLog("UI_TEST");
        rule.onNode(emailAddressTextField).performTextInput(email)
        rule.onNode(passwordTextField).performTextInput(password)
        rule.onNode(submitButton).performClick()

        Thread.sleep(1000)//pause or the following will fail - recommendation is an idle call back (not demonstrated here)
    }

    fun `enter email`(email:String = VALIDUSERNAME) {
        rule.onNode(emailAddressTextField).performTextInput(email)
    }

    //Used by add screen + home screen creating a user before editing
    fun enter_a_valid_user(){
        rule.onNode(medInfoTextField).performTextInput(MEDNAME)
        rule.onNode(detailsTextField).performTextInput(DETAILS)
        rule.onNode(dateButton).assertExists()

        rule.onNode(addButton).performClick()
        rule.onNode(addScreenText).assertDoesNotExist()
        Thread.sleep(1000)
    }

    fun enter_an_editted_valid_user(){
        rule.onNode(medInfoTextField).performTextInput(EDITNAME)
        rule.onNode(dateButton).assertExists()

        rule.onNode(editButton).performClick()
        Thread.sleep(1000)
    }

    fun enter_an_invalid_user(){
        rule.onNode(medInfoTextField).performTextInput("")
        rule.onNode(detailsTextField).performTextInput("")

        rule.onNode(addButton).performClick()

    }
}