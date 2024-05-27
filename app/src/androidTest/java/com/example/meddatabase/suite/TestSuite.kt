package com.example.meddatabase.suite


import com.example.meddatabase.components.CustomButtonTests
import com.example.meddatabase.components.CustomTextFieldTests
import com.example.meddatabase.screens.AddScreenTests
import com.example.meddatabase.screens.EditScreenTests
import com.example.meddatabase.screens.HomeScreenTests
import com.example.meddatabase.screens.LoginScreenTests
import com.example.meddatabase.screens.SearchScreenTests
import com.example.meddatabase.screens.SignUpScreenTests
import org.junit.runner.RunWith
import org.junit.runners.Suite
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CustomButtonTests::class,
    CustomTextFieldTests::class,
    LoginScreenTests::class,
    SignUpScreenTests::class,
    HomeScreenTests::class,
    AddScreenTests::class,
    EditScreenTests::class,
    SearchScreenTests::class,
)
class TestSuite{

}