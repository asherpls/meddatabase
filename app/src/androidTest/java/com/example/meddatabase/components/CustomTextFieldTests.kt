package com.example.meddatabase.components
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.meddatabase.presentation.components.CustomTextField
import com.example.meddatabase.presentation.theme.BottomNav1Theme
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder( MethodSorters.DEFAULT)
class CustomTextFieldTests {
    //Data to initialise component
    private val HINT_TEXT = "hint text"
    private val TEXT_TO_BE_DISPLAYED = "text"
    private var textInput = ""
    private val ERROR_MESSAGE_TEXT = ""
    private var errorIsNotPresent = true

    //Screen elements to test
    private val textToEnter = hasText(TEXT_TO_BE_DISPLAYED)
    private val hintText = hasText(HINT_TEXT)
    private val errorMessageText = hasText(ERROR_MESSAGE_TEXT)
    private val lines= 1

    @get:Rule
    var rule = createComposeRule()

    @Test
    fun `check default state of the textfield with text present`() {
        rule.setContent {
            CustomTextField(
                HINT_TEXT,
                TEXT_TO_BE_DISPLAYED,
                isPasswordField = false,
                onValueChange = { textInput = it },
                errorIsNotPresent,
                ERROR_MESSAGE_TEXT,
                lines
            )
        }

        rule.onNode(hintText).assertExists()
        rule.onNode(textToEnter).assertExists()

    }

    @Test
    fun `check state of the textfield with additional valid text input`() {
        rule.setContent {
            BottomNav1Theme {
                CustomTextField(
                    HINT_TEXT,
                    TEXT_TO_BE_DISPLAYED,
                    isPasswordField = false,
                    onValueChange = { textInput = it },
                    errorIsNotPresent,
                    ERROR_MESSAGE_TEXT,
                    lines
                )
            }
        }
        val ADDITIONAL_TEXT = "something"
        rule.onNode(hintText).assertExists()
        rule.onNode(textToEnter).performTextInput(ADDITIONAL_TEXT)

        //Cursor is not at the end and setting the mouse cursor as such requires modification to onValueChange
        assertEquals(textInput, ADDITIONAL_TEXT.plus(TEXT_TO_BE_DISPLAYED))
    }

    @Test
    fun `Textfield with display asterix of characters when set to password`() {

        var inputText = "test"

        rule.setContent {
            BottomNav1Theme {
                CustomTextField(
                    HINT_TEXT,
                    inputText,
                    isPasswordField = true,
                    onValueChange = { textInput = it },
                    errorIsNotPresent,
                    ERROR_MESSAGE_TEXT,
                    lines
                )
            }
        }
        //Replace contents with *
        inputText = inputText.replaceRange(0,inputText.length,"*")
        rule.onNodeWithText("****").assertExists()
    }
}