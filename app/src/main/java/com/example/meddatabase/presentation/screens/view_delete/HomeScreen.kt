package com.example.meddatabase.presentation.screens.view_delete

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.meddatabase.R
import com.example.meddatabase.data.medinfo.MedInfo
import com.example.meddatabase.presentation.components.BottomNavBar
import com.example.meddatabase.presentation.utils.Util
import androidx.compose.runtime.*
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.SmallSpacer
import com.google.firebase.auth.FirebaseUser


//vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory),

@SuppressLint("StateFlowValueCalledInComposition","UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    selectedIndex: Int,
    onClickToAdd: () -> Unit,
    onClickToEdit: () -> Unit,
    vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navController: NavHostController,
    selectedUser: FirebaseUser?,
    onIndexChange: (MedInfo?) -> Unit) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    )
    {Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.home),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        val userState by vm.contactState.collectAsState()

        if (userState.data.isNotEmpty()) //Some data to display
            LazyColumnWithSelection(
                vm,
                onIndexChange
            )

        if(vm.contactState.value.errorMessage.isNotBlank()){ //Problem retrieving data
            Util.showMessage(context,vm.contactState.value.errorMessage)
        }
        //buttons
        SmallSpacer()
        CustomButton(text = stringResource(R.string.add), onClickToAdd,Icons.Filled.Add)
        SmallSpacer()
        CustomButton(text = stringResource(R.string.edit), onClickToEdit,Icons.Filled.Edit)
        SmallSpacer()

    }}

}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LazyColumnWithSelection(vm: HomeViewModel,
                            onIndexChange: (MedInfo) -> Unit){
    var selectedIndexToHighlight by remember { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(vm.contactState.value.data) { index, item ->
            ItemView(
                index = index,
                item = item.toString(),
                selected = selectedIndexToHighlight == index,
                onClick = { index: Int ->
                    selectedIndexToHighlight =
                        index //local state for highlighting selected item
                    onIndexChange(item!!)             //for edit
                    vm.selectedMed = item       //for delete
                }
            )
            Divider(color = Color.Black, modifier = Modifier
                .fillMaxWidth()
                .height(1.dp))
        }
    }
}

@Composable
fun ItemView(index: Int,
             item: String,
             selected: Boolean,
             onClick: (Int) -> Unit){
    Text(
        text = "$item",
        modifier = Modifier
            .clickable {
                onClick.invoke(index)
            }
            .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(10.dp)
    )
}


