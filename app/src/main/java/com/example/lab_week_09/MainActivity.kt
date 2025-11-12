package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
// import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
// import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
// import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
// Impor baru dari Elements.kt
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the whole
                    // screen
                    modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the background
                    // color
                    //and set it as the color of the surface
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

//Declare a data class called Student
data class Student(
    var name: String
)

@Composable
fun Home() {
    //Here, we create a mutable state List of Student
    //We use remember to make the List remember its value
    //This is so that the List won't be recreated when the composable
    // recomposes
    //We use mutableStatelistof to make the List mutable
    //This is so that we can add or remove items from the List
    //If you're still confused, this is basically the same concept as
    //using
    //useState in React
    val listData = remember { mutableStateListOf(
        Student("Tanu"),
        Student("Tina"),
        Student("Tono")
    )}

    //Here, we create a mutable state of Student
    //This is so that we can get the value of the input field
    var inputField = remember { mutableStateOf(Student("")) }

    //We call the HomeContent composable
    //Here, we pass:
    //ListData to show the List of items inside HomeContent
    //inputField to show the input field value inside HomeContent
    //A Lambda function to update the value of the inputField
    //A Lambda function to add the inputField to the ListData
    HomeContent(
        listData,
        inputField.value,
        { input -> inputField.value = inputField.value.copy(input) },
        {
            listData.add(inputField.value)
            if (inputField.value.name.isNotBlank()) {
            }
            inputField.value = Student("")
        }
    )
}

//Here, we create a composable function called HomeContent
//HomeContent is used to display the content of the Home composable
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        //Here, we use item to display an item inside the LazyColumn
        item {
            Column(
                //Modifier.padding(16.dp) is used to add padding to the Column
                //You can also use Modifier.padding (horizontal = 16.dp, vertical
                // = 8.dp)
                //to add padding horizontally and vertically
                //or Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp,
                //bottom = 8.dp)
                //to add padding to each side
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                //Alignment.CenterHorizontally is used to align the Column
                //horizontally
                //You can also use verticalArrangement = Arrangement.Center to
                //align the Column vertically
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Here, we call the OnBackgroundTitleText UI Element
                OnBackgroundTitleText(text = stringResource(
                    id = R.string.enter_item)
                )

                //Here, we use TextField to display a text input field
                TextField(
                    //Set the value of the input field
                    value = inputField.name,
                    //Set the keyboard type of the input field
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    //Set what happens when the value of the input field changes
                    onValueChange = {
                        //Here, we call the onInputValueChange Lambda function
                        //and pass the value of the input field as a parameter
                        //This is so that we can update the value of the
                        //inputField
                        onInputValueChange(it)
                    }
                )

                //Here, we call the PrimaryTextButton UI Element
                PrimaryTextButton(text = stringResource(
                    id = R.string.button_click)
                ) {
                    onButtonClick()
                }
            }
        }

        //Here, we use items to display a list of items inside the LazyColumn
        //This is the RecyclerView replacement
        //We pass the listData as a parameter
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Here, we call the OnBackgroundItemText UI Element
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}