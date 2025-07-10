package com.example.authapp.ui.screen

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.authapp.viewmodel.AuthViewModel
import com.example.authapp.viewmodel.AuthState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.authState.collectAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is AuthState.Success) onRegisterSuccess()
    }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Registrar") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text("Usuário") }, value = username,
                onValueChange = { username = it }, singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                label = { Text("Email") }, value = email,
                onValueChange = { email = it }, singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                label = { Text("Senha") }, value = password,
                onValueChange = { password = it }, singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.register(username, email, password) }) { Text("Registrar") }
            TextButton(onClick = onNavigateToLogin) { Text("Já tenho conta") }

            if (state is AuthState.Loading) CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            if (state is AuthState.Error) Text((state as AuthState.Error).message, color = MaterialTheme.colorScheme.error)
        }
    }
}