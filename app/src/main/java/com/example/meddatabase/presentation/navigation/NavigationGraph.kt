package com.example.meddatabase.presentation.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess


sealed class NavScreen(var icon:Int, var route:String){
    data object Home : NavScreen(R.drawable.home, "Home")
    data object Add: NavScreen(R.drawable.add, "Add")
    data object Edit: NavScreen(R.drawable.add, "Edit")//drawable is not relevant
    data object Exit: NavScreen(R.drawable.logout, "Logout")
    data object Login: NavScreen(R.drawable.home, "Login")
    data object SignUp: NavScreen(R.drawable.home, "SignUp")
    data object Stats: NavScreen(R.drawable.calendar, "Stats")
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier.testTag("TestNavGraph")
) {
    //Avoid Bundle/Parcelable here - just store index of selected Contact
    var selectedContactIndex by remember{ mutableIntStateOf(1) }
    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    var selectedMedicine: MedInfo? =null

    NavHost(navController,
        startDestination = NavScreen.Login.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                selectedContactIndex,
                {navController.navigate("add")},
                onIndexChange = {
                    Log.v("OK","index change event called")
                    selectedMedicine = it
                },
                onClickToEdit = {
                    if (selectedMedicine != null) navController.navigate("edit")
                    else{
                        Log.v("double","asshole")
                }},
                navController = navController,
                selectedUser = currentFirebaseUser
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
            StatScreen(
                onClickToHome ={navController.navigate("home")},
                navController=navController)
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