package com.example.meddatabase.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.meddatabase.R
import com.example.meddatabase.core.MedApplication
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.presentation.screens.Stats.StatScreen
import com.example.meddatabase.presentation.screens.add.AddScreen
import com.example.meddatabase.presentation.screens.edit.EditScreen
import com.example.meddatabase.presentation.screens.login.LoginScreen
import com.example.meddatabase.presentation.screens.signup.SignupScreen
import com.example.meddatabase.presentation.screens.view_delete.HomeScreen
import kotlin.system.exitProcess


sealed class NavScreen(var icon:Int, var route:String){
    data object Home : NavScreen(R.drawable.home, "Home")
    data object Add: NavScreen(R.drawable.add, "Add")
    data object Edit: NavScreen(R.drawable.add, "Edit")//drawable is not relevant
    data object Exit: NavScreen(R.drawable.logout, "Logout")
    data object Login: NavScreen(R.drawable.home, "Login")
    data object SignUp: NavScreen(R.drawable.home, "SignUp")
    data object Stats: NavScreen(R.drawable.search, "Search")
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier.testTag("TestNavGraph")
) {
    var selectedMedicine: MedInfo? =null

    NavHost(navController,
        startDestination = NavScreen.Login.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                {navController.navigate("add")},
                onIndexChange = {
                    selectedMedicine = it
                },
                onClickToEdit = {
                    if (selectedMedicine != null) navController.navigate("edit")
                    },
                navController = navController,
            );
        }
        composable(NavScreen.Login.route) {
            LoginScreen(
                navigateToHomeScreen ={navController.navigate("home")},
                navigateToSignUpScreen = {navController.navigate("signup")})
        }
        composable(NavScreen.SignUp.route) {
            SignupScreen(
                navigateBack = {navController.popBackStack()}
            )
        }

        composable(NavScreen.Stats.route) {
            StatScreen(navController=navController)
        }

        composable(NavScreen.Add.route) {
            AddScreen(
                onClickToHome ={navController.navigate("home")})
        }
        composable(NavScreen.Edit.route) {
            if(selectedMedicine!= null){
            EditScreen(selectedContactIndex=selectedMedicine,
                onClickToHome = {navController.navigate("home")})}
        }
        composable(NavScreen.Exit.route){
            MedApplication.container.authRepository.signOut()
            exitProcess(0)
        }
    }
}