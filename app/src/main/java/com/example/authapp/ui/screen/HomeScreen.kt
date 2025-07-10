package com.example.authapp.ui.screen

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.authapp.viewmodel.AuthViewModel
import com.example.authapp.viewmodel.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.authState.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Home") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (state is AuthState.Success) {
                    val user = (state as AuthState.Success).user
                    Text("Bem-vindo, ${user.username}!")
                    Text("Email: ${user.email}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.logout()
                    onLogout()
                }) { Text("Logout") }
            }
        }
    }
}