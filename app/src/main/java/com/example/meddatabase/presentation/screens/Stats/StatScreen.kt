package com.example.meddatabase.presentation.screens.Stats


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.meddatabase.R
import com.example.meddatabase.presentation.components.BottomNavBar
import com.example.meddatabase.presentation.components.CustomButton
import com.example.meddatabase.presentation.components.CustomTextField
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatScreen(onClickToHome: () -> Unit,
               navController: NavHostController
){
    val datePickerState = rememberDateRangePickerState(yearRange = 2024..2025,initialSelectedStartDateMillis = null,
        initialDisplayedMonthMillis = null,initialDisplayMode = DisplayMode.Picker)


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
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            LazyColumnWithSelection(
                datePickerState
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LazyColumnWithSelection(datePickerState: DateRangePickerState){
    var selectedIndexToHighlight by remember { mutableStateOf(-1) }
    //get current month
    val calendar = Calendar.getInstance()
    val monthDateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val currentMonthName = monthDateFormat.format(calendar.time)

    // determine number of days
    val daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // create a list of days in current month
    val dateList = ArrayList<String>()
    for (day in 1..daysInCurrentMonth) {
        dateList.add("$currentMonthName $day")
    }

    //new stuff
    val date= remember {
        LocalDate.now()
    }
    val datesList = ArrayList<LocalDate>()
    for(dates in 1..30 ){
        datesList.add(date.plusDays(1))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(dateList) { index, item ->
            ItemView(
                index = index,
                item = item.toString(),
                selected = selectedIndexToHighlight == index,
                onClick = { index: Int ->
                    selectedIndexToHighlight =
                        index //local state for highlighting selected item //for edit
                },
            )
        }
    }
}

@Composable
fun ItemView(index: Int,
             item: String,
             selected: Boolean,
             onClick: (Int) -> Unit,
){

    Column {
        androidx.compose.material.Text(
            text = "$item",
            modifier = Modifier
                .clickable {
                    onClick.invoke(index)
                }
                .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
                .fillMaxWidth()
                .padding(10.dp)
        )

        Row{
        for (day in 1..3){
            AssistChip(
                label = { androidx.compose.material3.Text("Assist chip") },
                onClick = { Log.d("Assist chip", "hello world") }
            )
        }}

    }

}