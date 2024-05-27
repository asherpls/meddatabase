package com.example.meddatabase.screens

import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder( MethodSorters.NAME_ASCENDING)
class SearchScreenTests : ScreenTests(){
    @Before
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `go to the search screen`() {
        `sign in`()
        rule.onNode(searchNavBarItem).performClick()
        rule.onNode(searchScreenText).assertExists()
    }

    @Test
    fun `check that search list exists`() {
        `sign in`()
        rule.onNode(addButton).performClick()
        `enter_a_valid_user`()

        //Should be auto returned to the home screen
        rule.onNode(homeScreenText).assertExists()
        //Check entry exists in list view
        rule.onNode(searchNavBarItem).performClick()
        rule.onNode(fullListItem).assertExists()
        rule.onNode(homeNavBarItem).performClick()
        //delete entry
        rule.onNode(listItem).performClick()
        rule.onNode(editButton).performClick()
        rule.onNode(deleteButton).performClick()
    }
}