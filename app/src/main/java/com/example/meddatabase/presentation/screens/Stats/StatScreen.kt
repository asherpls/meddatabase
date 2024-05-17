package com.example.meddatabase.presentation.screens.Stats


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.meddatabase.R
import com.example.meddatabase.presentation.components.BottomNavBar
import com.example.meddatabase.presentation.components.SmallSpacer
import androidx.compose.material3.MaterialTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatScreen(navController: NavHostController,
               vm: StatViewModel = viewModel(factory = StatViewModel.Factory),
){
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.stats),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black,
            )
            SmallSpacer()

            // search field
            SearchView(textState)
            SmallSpacer()

            //full list
            val userState by vm.contactState.collectAsState()
            if (userState.data.isNotEmpty()) //Some data to display
                LazyColumnWithSelection(
                    vm,
                    textState
                )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LazyColumnWithSelection(
    vm: StatViewModel,
    searchData: MutableState<TextFieldValue>?
){
    var selectedIndexToHighlight by remember { mutableStateOf(-1) }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ){
        val searchedText = searchData?.value?.text
        itemsIndexed(vm.contactState.value.data) { index, item ->
            if (item != null) {
                if (searchedText.toString() in item.toFatString()) {
                    ItemView(
                        item = item.toFatString(),
                        selected = selectedIndexToHighlight == index,
                    )
                    Divider(
                        color = Color.Gray, modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemView(             item: String,
             selected: Boolean,
){
    Text(
        text = "$item",
        modifier = Modifier
            .background(if (selected) MaterialTheme.colorScheme.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(10.dp)
    )
}


//---------------------------------------------------
@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value -> state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )
}


