package com.alicankesen.contactapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonRegisterPage() {
    var personName by remember {
        mutableStateOf("")
    }
    var personPhone by remember {
        mutableStateOf("")
    }
    //Geri tuşuna basıldığında ilk Textfield'daki seçimi kaldıracak
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kişiler") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200)
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                TextField(
                    value = personName,
                    onValueChange = { personName = it },
                    label = { Text(text = "Kişi İsmi") },
                )
                TextField(
                    value = personPhone,
                    onValueChange = { personPhone = it },
                    label = { Text(text = "Kişi Telefonu") },
                )
                Button(onClick = {
                    var person_name = personName
                    var person_phone = personPhone
                    Log.d("Kişi Kayıt", "$person_name - $person_phone")
                    localFocusManager.clearFocus()

                }, modifier = Modifier.size(250.dp, 50.dp)) {
                    Text(text = "Kişiyi Kaydet")
                }


            }
        }
    )
}
