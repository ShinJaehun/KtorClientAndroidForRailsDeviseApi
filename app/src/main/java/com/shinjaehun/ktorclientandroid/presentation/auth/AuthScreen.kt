package com.shinjaehun.ktorclientandroid.presentation.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigate:(String) -> Unit
) {
    // 이렇게 처리하니까 세상에 계속 이 결과를 내면서 onNavigate가 호출되었던 것이었다...
//    val isVerify by remember { viewModel.verificationNeeded }
//    Log.i(TAG, "isVerify: $isVerify")
//    if(isVerify){
//        onNavigate("posts_screen")
//    }
    SignInForm(viewModel, onNavigate)
}

@Composable
fun SignInForm(
    viewModel: AuthViewModel,
    onNavigate: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // viewModel.performSignIn() 이후에 TextField를 정리하기 위해
    // viewModel로 넘기려고 했는데... TextField가 또 쌩뚱 맞게 동작한다...

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText.trimEnd()
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.performSignIn(context, email, password, onNavigate)
                // onNavigate("posts_screen")
                // 음... 이렇게 하면 로그인 실패해도 posts show 합니다. 바보 븅시나
                email = ""
                password = ""
                // 그냥 이렇게만 해도 clear되기는 함...
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Sign In")
        }
    }
}