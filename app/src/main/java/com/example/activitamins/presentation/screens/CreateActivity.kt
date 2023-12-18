package com.example.activitamins.presentation.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.activitamins.data.Activities
import com.example.activitamins.presentation.components.ui.FormItem
import com.example.activitamins.presentation.components.ui.PageTitle
import com.example.activitamins.viewModel.ActivitiesViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun CreateActivity(
    activitiesViewModal: ActivitiesViewModel
) {

    var name by remember { mutableStateOf("") }
    fun setName(it: String) {
        name = it
    }

    var date by remember {
        mutableStateOf<Date>(
            Date()
        )
    }

    fun setDate(it: Date) {
        date = it
    }


    var organizer by remember { mutableStateOf("") }
    fun setOrganizer(it: String) {
        organizer = it
    }

    var description by remember { mutableStateOf("") }
    fun setDescription(it: String) {
        description = it
    }

    val activityCreatedToast =
        Toast.makeText(LocalContext.current, "Activity Created", Toast.LENGTH_SHORT)

    fun clearFields() {
        setName("")
        setOrganizer("")
        setDescription("")
        setDate(Date())
    }

    fun createActivity() {
        val newActivity =
            Activities(
                activitiesViewModal.uiState.value.nextActivityId,
                name,
                date,
                description = "",
                organizer = organizer
            )

        activitiesViewModal.addActivity(newActivity)
        clearFields()
        activityCreatedToast.show()
    }

    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            setDate(Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
            }.time)

        }, year, month, dayOfMonth
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        PageTitle(title = "Create Activity")

        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            FormItem(label = "Activity Name", inputVal = name, onInputChange = {
                setName(it)
            })

            FormItem(
                label = "Organizer",
                inputVal = organizer,
                onInputChange = { setOrganizer(it) })

            FormItem(
                label = "Description",
                inputVal = description,
                onInputChange = { setDescription(it) })

            Button(
                shape = MaterialTheme.shapes.extraSmall,
                onClick = {
                    datePicker.show()
                }) {
                Text(text = "Select a date")
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                shape = MaterialTheme.shapes.extraSmall,
                onClick = {
                    createActivity()
                }) {
                Text(text = "Create Activity")
            }
        }
    }
}