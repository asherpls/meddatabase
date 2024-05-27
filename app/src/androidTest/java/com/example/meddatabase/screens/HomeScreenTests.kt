package com.example.meddatabase.screens

import androidx.compose.ui.test.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder( MethodSorters.NAME_ASCENDING)
class HomeScreenTests : ScreenTests(){
    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `check default state of the home screen`() {
        `sign in`()
        rule.onNode(homeScreenText).assertExists()

        rule.onNode(bottomNavBar).assertExists()
        rule.onNode(searchNavBarItem).assertExists()
        rule.onNode(exitNavBarItem).assertExists()
        rule.onNode(homeNavBarItem).assertExists()
    }

    @Test
    fun `go to the search screen`() {
        `sign in`()
        rule.onNode(searchNavBarItem).performClick()
    }

    @Test
    fun `logout`(){
        `sign in`()
        rule.onNode(exitNavBarItem).performClick()
    }

    @Test
    fun `go to add screen`(){
        `sign in`()
        rule.onNode(addButton).performClick()
        rule.onNode(addScreenText).assertExists()
    }

    @Test
    fun `go to edit screen`(){
        `sign in`()
        //Add a contact to view
        rule.onNode(addButton).performClick()
        `enter_a_valid_user`()

        //select and edit the contact
        rule.onNode(listItem).performClick()
        rule.onNode(editButton).performClick()
        rule.onNode(editScreenText).assertExists()

        // Tidy up - Delete the contact
        rule.onNode(deleteButton).performClick()
    }
}