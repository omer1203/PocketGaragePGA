
//REGISTRATION SCREEN - Omer

package com.example.homepage


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


import com.example.homepage.R





// function that creates the weave design for the upper half in the background
@Composable
fun TopDesign() { // one half of the screen design, the wavy design
    Box(
        modifier = Modifier // ensure that the entire screen is filled
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.wave), // get the image from the wave.xml
            contentDescription = "Top Design",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}

// function that gives the gradient colored background
@Composable
fun linearGradient(
    isVerticalGradient: Boolean,
    colors: List<Color>
): Brush {
    val endOffset = if (isVerticalGradient) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}

@Composable
fun RegistrationScreen(navController: NavController, onRegistrationComplete: () -> Unit = {}) {
    // start by initializing the variables for user input by remember
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val userID = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val chosenQuestion = remember { mutableStateOf("") }
    val answer = remember { mutableStateOf("") }

    //states that manage validation errors
    val firstNameError = remember { mutableStateOf(false) }
    val lastNameError = remember { mutableStateOf(false) }
    val userIDError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val phoneError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val questionError = remember { mutableStateOf("") }
    val answerError = remember { mutableStateOf(false) }

    // list of security questions
    val securityQuestions = listOf(
        "What was your first car?",
        "What is the name of the city where you were born?",
        "What is your mother's maiden name?"
    )

    val isDuplicate = remember { mutableStateOf(false)}
    val duplicatePopUp = remember { mutableStateOf("")}


    val expanded = remember { mutableStateOf(false) } // to handle drop-down menu
    val generalError = remember { mutableStateOf("") } // error message
    val colorList = listOf(Color(0xFF470404), Color(0x6F1F1F)) // color list for the gradient in the background

    //Start of the registration screen layout
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
                    text = "Register",
                    style = MaterialTheme.typography.headlineSmall // choosing headlineSmall which is big but too big
                )

                Spacer(modifier = Modifier.height(10.dp)) // create distance between each entry
                Row( // this row is for the first and last name so it can be side by side
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp) // spacing between the input boxes
                ) {
                    //text input field for first name
                    CreateOutText(
                        value = firstName,
                        keyboardTyping = KeyboardType.Text,
                        text = "First Name",
                        modifier = Modifier
                            .weight(1f) // 1f to divide
                            .height(60.dp),
                        iserror = firstNameError.value // this is in case of error
                    )

                    // text input field for last name
                    CreateOutText(
                        value = lastName,
                        keyboardTyping = KeyboardType.Text,
                        text = "Last Name",
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp),
                        iserror = lastNameError.value  // to highlight the field in case of error
                    )
                }
                Spacer(modifier = Modifier.height(6.dp)) // create distance between each entry

                //Creating the UserID input field using the CreateOutText() function
                CreateOutText(
                    value = userID,
                    keyboardTyping = KeyboardType.Text,
                    text = "UserID",
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = userIDError.value  // to highlight the field in case of error
                )

                // requirements for the UserID right under the userID input field
                Text(
                    text = "(minimum 8 characters, no special characters)",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic, // font style italic to catch attention
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp)) // create distance between each entry

                //input field for email
                CreateOutText(
                    value = email,
                    keyboardTyping = KeyboardType.Email, // email format keyboard type
                    text = "Email Address", // text to be shown in the field
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = emailError.value  // to highlight the field in case of error
                )
                Spacer(modifier = Modifier.height(6.dp)) // create distance between each entry

                // input field for phone number
                CreateOutText(
                    value = phone,
                    keyboardTyping = KeyboardType.Phone,
                    text = "Phone Number",
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = phoneError.value
                )
                Spacer(modifier = Modifier.height(6.dp)) // create distance between each entry

                //input field for password
                CreateOutText(
                    value = password,
                    keyboardTyping = KeyboardType.Password,
                    text = "Create a Password",
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    iserror = passwordError.value  // to highlight the field in case of error
                )

                //similar to the userID, this shows the password requirements
                Text(
                    text = "(Password must contain at least one number, one uppercase letter and one special character)",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))

                //Creating a box container for security question drop down and input
                Box {
                    OutlinedTextField(
                        value = chosenQuestion.value, // selcted question
                        onValueChange = {}, // no input since it is read only
                        readOnly = true, // makes sure it is read only and not editable
                        label = { Text("Choose a Security Question") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded.value = true },
                        trailingIcon = { // Icon for the drop down functionality
                            Icon(Icons.Default.ArrowDropDown, // basic down drop down icon
                                contentDescription = "Drop-Down icon",
                                modifier = Modifier.clickable { expanded.value = true }) // make it clickable
                        },
                        isError = questionError.value.isNotEmpty() // to highlight the field in case of error
                    )
                    DropdownMenu(
                        expanded = expanded.value, // if expanded
                        onDismissRequest = { expanded.value = false })// closes the dropdown if you click outside
                    {
                        securityQuestions.forEach { question -> // iterate over the list of questions
                            DropdownMenuItem(
                                text = { Text(question) }, // displaying each question as item in the dropdown menu
                                onClick = {
                                    chosenQuestion.value = question // chooses the specific querstion on click
                                    expanded.value = false // closes the dropdown menu
                                    questionError.value = "" // clears error in case
                                }
                            )
                        }

                    }
                    if (questionError.value.isNotEmpty()) { // if there is an error
                        Text(
                            text = questionError.value, // displays the error shown in the validation class
                            color = Color.Red,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(10.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(6.dp)) // create distance between each entry

                //input field for the security question answer
                CreateOutText(
                    value = answer,
                    keyboardTyping = KeyboardType.Text,
                    text = "Answer of Security Question",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    iserror = answerError.value // to highlight the field in case of error
                )

                Spacer(modifier = Modifier.height(25.dp)) // to create more space between the ned and the register button

                //register button which handles validation
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val duplicateMessage = checkDuplicate(userID.value, email.value, phone.value) // check for duplicates
                        val isValid = validateAll(
                            firstName.value, firstNameError,
                            lastName.value, lastNameError,
                            userID.value, userIDError,
                            email.value, emailError,
                            phone.value, phoneError,
                            password.value, passwordError,
                            chosenQuestion.value, questionError,
                            answer.value, answerError
                        )
                        if (duplicateMessage != null) { // if there are duplicates
                            isDuplicate.value = true
                            duplicatePopUp.value = duplicateMessage // show the duplicates
                        }
                        else if (isValid) {
                            onRegistrationComplete() // if everything is valid, then go to home screen
                        }
                    }
                ) {
                    Text("Register")
                }

                Spacer(modifier = Modifier.height(10.dp)) // to create more space between the ned and the register button

                // In the case the user already has an acount
                Text(
                    text = "Already Registered? Click here to login.",
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        navController.navigate("login") // go to the login page
                    }
                )


                //
                if (isDuplicate.value) { // in the case there are duplicates
                    AlertDialog( // create an alertBox
                        onDismissRequest = { isDuplicate.value = false }, // dismiss the popUp
                        confirmButton = {
                            TextButton(
                                onClick = { isDuplicate.value = false }
                            ) {
                                Text("OK")
                            }
                        },
                        title = { Text("Duplicate Entry") },
                        text = { Text(duplicatePopUp.value) } // List the duplicates
                    )
                }



            }

        }
    }
}


//creating a function to easily make the outlinedText to save repetition/ writing code including error
@Composable
fun CreateOutText(
    value: MutableState<String>, // the current input text
    keyboardTyping: KeyboardType, // keyboard type
    text: String, // label for the3 text field
    modifier: Modifier = Modifier, // Modiefier for layout and style
    iserror: Boolean // error indication
) {
    OutlinedTextField(
        value = value.value, // current value of the text field
        modifier = modifier.fillMaxWidth(), // make the field max
        onValueChange = { value.value = it }, // updating the field when the inputted
        label = { Text(text, fontSize = 16.sp) }, // displaying the label
        keyboardOptions = KeyboardOptions(keyboardType = keyboardTyping), // assigning the keyboard type
        singleLine = true, // input must be in one line
        isError = iserror// highlight the field in the case of an error
    )

}


// VALIDATION FUNCTIONS INSIDE VALIDATOR CLASS
class Validator {
    companion object {
        //function to validate the first name, ensures not blank and all letters
        fun validateFirstName(firstName: String): Boolean {
            return firstName.isNotBlank() && firstName.all { it.isLetter() }
        }

        //the same thign with the last name
        fun validateLastName(lastName: String): Boolean {
            return lastName.isNotBlank() && lastName.all { it.isLetter() }
        }

        //UserID validation, length of at least 8 and only letters or digits
        fun validateUserID(userID: String): Boolean {
            return userID.length >= 8 && userID.all {
                it.isLetterOrDigit()
            }
        }

        //email address validation, ensures it follows the email format
        fun validateEmailAddress(email: String): Boolean { // checking if email matches the format
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        //phone number validation, ensures its 10 digits
        fun validatePhoneNumber(phone: String): Boolean {
            return phone.length == 10 && phone.all { it.isDigit() }
        }

        // password validation, ensures it meets all passport requirements as shown below
        fun validatePassword(password: String): Boolean { // password validation
            return password.length >= 8 &&
                    password.any { it.isDigit() } && // have one digit
                    password.any { it.isLowerCase() } && // have one lowercase
                    password.any { it.isUpperCase() } &&// have one uppercase
                    password.any { !it.isLetterOrDigit() } // have a special character
        }
    }

}


//Function that validated all input fields and errors
fun validateAll(
    firstName: String, firstNameError: MutableState<Boolean>, // mutable since it will change
    lastName: String, lastNameError: MutableState<Boolean>,
    userID: String, userIDError: MutableState<Boolean>,
    email: String, emailError: MutableState<Boolean>,
    phone: String, phoneError: MutableState<Boolean>,
    password: String, passwordError: MutableState<Boolean>,
    question: String, questionError: MutableState<String>,
    answer: String, answerError: MutableState<Boolean>
): Boolean {

    //validate first name
    firstNameError.value = !Validator.validateFirstName(firstName)
    //validating last name
    lastNameError.value = !Validator.validateLastName(lastName)
    // validating userID
    userIDError.value = !Validator.validateUserID(userID)
    //validate email
    emailError.value = !Validator.validateEmailAddress(email)
    // validate phone
    phoneError.value = !Validator.validatePhoneNumber(phone)
    //validate password
    passwordError.value = !Validator.validatePassword(password)
    // vaidate question
    questionError.value = if (question.isNotBlank()) { "" } else { "Select a security question." }
    //validate answer
    answerError.value = answer.isBlank()

    // return if all the fields are valid or not
    return !firstNameError.value &&
            !lastNameError.value &&
            !userIDError.value &&
            !emailError.value &&
            !phoneError.value &&
            !passwordError.value &&
            questionError.value.isEmpty() &&
            !answerError.value
}

//FUNCTION THAT CHECKS FOR DUPLICATES WHILE REGISTERING
fun checkDuplicate(userID: String, email: String, phone: String): String?{
    val existsErrors = mutableListOf<String>()

    val existsUserID = "DoesExist"
    if (userID == existsUserID) { // here it will check with the database if it already exists
        existsErrors.add( "UserID already exists")
    }

    val existsEmail = "abc@gmail.com"
    if(email == existsEmail)  {// here it will check with the database if it already exists
        existsErrors.add("Email already exists")
    }

    val existsPhone = "1234567890"
    if(phone == existsPhone)  {// here it will check with the database if it already exists
        existsErrors.add("Phone number already exists")
    }

    return if(existsErrors.isNotEmpty()) { // if there exists any errors
        existsErrors.joinToString("\n") // returns all of them in different line
    }
    else null

}


//This is just to show preview, remove later if neccessary
@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    MaterialTheme {
        RegistrationScreen(navController = rememberNavController())
    }
}