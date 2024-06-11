package com.alicankesen.contactapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alicankesen.contactapp.entity.Person
import com.alicankesen.contactapp.ui.theme.ContactAppTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageTransaction()
                }
            }
        }
    }
}

@Composable
fun PageTransaction(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_page"){
        composable("home_page"){
            HomePage(navController = navController)
        }
        composable("person_register"){
           PersonRegisterPage()
        }
        composable("person_detail/{person}", arguments = listOf(
            navArgument("person"){type = NavType.StringType}
        )){
            val json = it.arguments?.getString("person")!!
            val personObject = Gson().fromJson(json, Person::class.java)
            PersonDetailPage(person = personObject)
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {

    var isSearch by remember {
        mutableStateOf(false)
    }
    var tf by remember {
        mutableStateOf("")
    }
    var personList = remember {
        mutableStateListOf<Person>()
    }

    LaunchedEffect(key1 = true) {
        val p1 = Person(id = 1, name = "Ali", phone = "0531 399 9932")
        val p2 = Person(id = 2, name = "Can", phone = "0530 399 9932")
        val p3 = Person(id = 3, name = "Ahmet", phone = "0530 399 9932")
        personList.add(p1)
        personList.add(p2)
        personList.add(p3)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearch) {
                        TextField(
                            value = tf,
                            onValueChange = {
                                tf = it
                                Log.d("Kişi arama", it)
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                textColor = Color.White,

                                )
                        )
                    } else {
                        Text(text = "Kişiler", color = Color.White)
                    }
                },
                actions = {
                    if (isSearch) {
                        IconButton(onClick = {
                            isSearch = false
                            tf = ""
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_icon),
                                contentDescription = "",
                                tint = Color.White,
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearch = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = "",
                                tint = Color.White,
                            )
                        }
                    }
                },

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200)
                )
            )
        },
        content = {

            Box(Modifier.padding(it)) {
                LazyColumn {
                    println("LİSTE SAYISI" + personList.count())
                    items(
                        personList.count(),
                        itemContent = {
                            val persons = personList[it]
                            Card(
                                modifier = Modifier
                                    .padding(all = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.clickable {
                                    val personJson = Gson().toJson(persons)
                                    navController.navigate("person_detail/${personJson}")
                                }) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(all = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(text = "${persons.name} - ${persons.phone}")

                                        IconButton(onClick = {
                                            personList.remove(persons)
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.delete_icon),
                                                contentDescription = "",
                                                tint = Color.Gray,
                                            )
                                        }

                                    }
                                }
                            }
                        },
                    )


                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                          navController.navigate("person_register")
                },
                containerColor = colorResource(id = R.color.teal_200),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = "",
                        tint = Color.White,
                    )
                }
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactAppTheme {
    }
}