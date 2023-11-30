package com.example.newswave
//import android.content.Intent
//import androidx.activity.ComponentActivity
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//
//class GoogleLoginScreen : ComponentActivity() {
//    private var email by remember { mutableStateOf("") }
//    private var password by remember { mutableStateOf("") }
//    private val snackbarHostState = remember { SnackbarHostState() }
//
//    private val googleSignInOptions: GoogleSignInOptions by lazy {
//        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//    }
//
//    private val googleSignInClient by lazy {
//        GoogleSignIn.getClient(this, googleSignInOptions)
//    }
//
//    private val startGoogleSignIn: ActivityResultLauncher<Intent> =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                try {
//                    val account = task.getResult(ApiException::class.java)
//                    // Handle the successful Google sign-in, e.g., authenticate the user with your server.
//                } catch (e: ApiException) {
//                    // Handle the failed sign-in
//                }
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            GoogleLoginScreenContent(
//                email,
//                password,
//                onEmailChange = { email = it },
//                onPasswordChange = { password = it },
//                onSignInClick = { signInWithGoogle() },
//                snackbarHostState
//            )
//        }
//    }
//
//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startGoogleSignIn.launch(signInIntent)
//    }
//}
//
//@Composable
//fun GoogleLoginScreenContent(
//    email: String,
//    password: String,
//    onEmailChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//    onSignInClick: () -> Unit,
//    snackbarHostState: SnackbarHostState
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        GoogleSignInButton(
//            onClick = onSignInClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        BasicTextField(
//            value = email,
//            onValueChange = onEmailChange,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Email
//            ),
//            keyboardActions = KeyboardActions(
//                onNext = { /* Handle next action if needed */ }
//            ),
//            placeholder = { Text("Email") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFF5F5F5))
//                .padding(16.dp)
//                .height(56.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        BasicTextField(
//            value = password,
//            onValueChange = onPasswordChange,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Password
//            ),
//            keyboardActions = KeyboardActions(
//                onDone = { /* Handle done action if needed */ }
//            ),
//            placeholder = { Text("Password") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFF5F5F5))
//                .padding(16.dp)
//                .height(56.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { /* Handle the login button click */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//        ) {
//            Text("Sign In")
//        }
//    }
//}
//
//@Composable
//fun GoogleSignInButton(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    val snackbarController = rememberUpdatedState(SnackbarController(hostState = snackbarHostState))
//
//    Column {
//        SnackbarHost(
//            hostState = snackbarHostState,
//            snackbar = { data ->
//                Snackbar(
//                    snackbarData = data,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        )
//
//        GoogleSignInButton(
//            onClick = {
//                onClick()
//                snackbarController.current?.showSnackbar("Signing in with Google")
//            },
//            modifier = modifier
//        )
//    }
//}
