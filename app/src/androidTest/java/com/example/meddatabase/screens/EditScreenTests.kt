package com.example.meddatabase.screens

import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Test

class EditScreenTests: ScreenTests() {
    @Before
    override fun setUp() {
        super.setUp()
    }


    private fun `go to the edit screen`(){
        `sign in`()
        //Add a contact to view
        rule.onNode(addButton).performClick()
        enter_a_valid_user()
        //select and edit the contact
        rule.onNode(listItem).performClick()
        rule.onNode(editButton).performClick()
    }

    @Test
    fun `check the default state of the edit screen`(){
        `go to the edit screen`()

        // Tidy up - Delete the contact
        rule.onNode(deleteButton).performClick()
    }

    @Test
    fun `edit a  the default state of the edit screen`(){
        `go to the edit screen`()

        //enter values and click submit
        enter_an_editted_valid_user()

        //check if list item shows updated values
        rule.onNode(editedListItem).assertExists()
        rule.onNode(editedListItem).performClick()

        // Tidy up - Delete the contact
        rule.onNode(editButton).performClick()
        rule.onNode(deleteButton).performClick()



    }
}