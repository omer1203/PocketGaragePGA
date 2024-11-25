package com.example.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.homepage.R



@Composable
fun LoginScreen(navController: NavController) {
    val userID = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val userIDError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false)}
    val existsPopUp = remember { mutableStateOf(false) }

    val colorList = listOf(Color(0xFF470404), Color(0x6F1F1F)) // color list for the gradient in the background

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = linearGradient(
                    isVerticalGradient = true,
                    colors = colorList
                )

            )
    ) {
        Box(
            modifier = Modifier
                .padding(7.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(elevation = 6.dp)
        ) {
            Box( // this box is to show the wave design, with a padding
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .padding(20.dp)

            ) {
                TopDesign()
            }
            Column(// create a column to display the registration form
                verticalArrangement = Arrangement.Center, // ensuring everythign is centered vertically
                horizontalAlignment = Alignment.CenterHorizontally,// and horizontally
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)) // rounded edge for cleaner look
                    .padding(40.dp) // padding also for better design
            ) {
                Text( // header for registration
                    text = "Login",
                    style = MaterialTheme.typography.headlineSmall // choosing headlineSmall which is big but too big
                )

                Spacer(modifier = Modifier.height(10.dp)) // create distance between each entry
                CreateOutText(
                    value = userID,
                    keyboardTyping = KeyboardType.Text,
                    text = "UserID",
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = userIDError.value  // to highlight the field in case of error
                )
                Spacer(modifier = Modifier.height(10.dp)) // create distance between each entry

                CreateOutText(
                    value = password,
                    keyboardTyping = KeyboardType.Password,
                    text = "Your Password",
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = passwordError.value  // to highlight the field in case of error
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        //check for empty fields
                        userIDError.value = userID.value.isBlank()
                        passwordError.value = password.value.isBlank()

                        // if there are values
                        if (!userIDError.value && !passwordError.value) {
                            val isValidLogin = validateLogin(userID.value, password.value)//validate it by checking thorugh the database if it existes
                            if (isValidLogin) {
                                navController.navigate("home") // go to the home screen if login successful
                            }
                            else {
                                existsPopUp.value = true //if the login fails
                            }
                        }
                    }
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Option to go to register if user has not yet registered
                Text(
                    text = "Not registered? Click here to register.",
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        navController.navigate("registration") // go to registration screen
                    }
                )

            }
        }


    }
    if (existsPopUp.value) { //in the case that user or password does not exist
        AlertDialog(
            onDismissRequest = { existsPopUp.value = false },
            title = { Text(text = "Login Failed") },
            text = { Text("Your username or password is incorrect. Are you registered?") },
            confirmButton = {
                Button(
                    onClick = {
                        existsPopUp.value = false
                        navController.navigate("registration") //option to go to registration
                    }
                ) {
                    Text("Register")
                }
            },
            dismissButton = {
                Button(
                    onClick = { existsPopUp.value = false }
                ) {
                    Text("Try Again") // option to try again
                }
            }
        )
    }


}


//Validating Login, will check with the database here and return if matches or not
fun validateLogin(userID: String, password: String): Boolean {
    //database query using userID as the primary key to check if it exists
    //check if same user password is correct
    return userID == "omer112" && password == "password" // Example Test Case, enter this to login
}





//Function to Preview the login page
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        LoginScreen(navController = rememberNavController())
    }
}
