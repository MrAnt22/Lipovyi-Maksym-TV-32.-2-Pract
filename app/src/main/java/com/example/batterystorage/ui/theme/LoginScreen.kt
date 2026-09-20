package com.example.batterystorage.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.batterystorage.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    val scope = rememberCoroutineScope()

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isRegistration by remember {
        mutableStateOf(false)
    }

    if (isRegistration) {

        RegistrationScreen(
            onBackToLogin = {
                isRegistration = false
            }
        )

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Battery Storage",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Вхід до системи накопичення енергії",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = login,
                onValueChange = {
                    login = it
                    message = ""
                },
                label = {
                    Text("Логін")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    message = ""
                },
                label = {
                    Text("Пароль")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {

                    if (login.isBlank()) {
                        message = "Введіть електронну пошту"
                        return@Button
                    }

                    if (password.isBlank()) {
                        message = "Введіть пароль"
                        return@Button
                    }

                    if (password.length < 6) {
                        message = "Пароль повинен містити щонайменше 6 символів"
                        return@Button
                    }

                    scope.launch {

                        isLoading = true

                        val success = userPreferences.checkUser(
                            email = login,
                            password = password
                        )

                        isLoading = false

                        if (success) {
                            message = "Авторизація успішна"
                            onLoginSuccess()
                        } else {
                            message = "Неправильний email або пароль"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Увійти")
                }
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            TextButton(
                onClick = {
                    isRegistration = true
                    message = ""
                }
            ) {
                Text("Немає облікового запису? Зареєструватися")
            }
        }
    }
}

@Composable
fun RegistrationScreen(
    onBackToLogin: () -> Unit
) {

    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    val scope = rememberCoroutineScope()

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Battery Storage",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Реєстрація користувача",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                message = ""
            },
            label = {
                Text("Ім'я")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                message = ""
            },
            label = {
                Text("Електронна пошта")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                message = ""
            },
            label = {
                Text("Пароль")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                message = ""
            },
            label = {
                Text("Підтвердження пароля")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                if (name.isBlank()) {
                    message = "Введіть ім'я"
                    return@Button
                }

                if (email.isBlank()) {
                    message = "Введіть електронну пошту"
                    return@Button
                }

                if (!email.contains("@")) {
                    message = "Введіть коректну електронну пошту"
                    return@Button
                }

                if (password.length < 6) {
                    message = "Пароль повинен містити щонайменше 6 символів"
                    return@Button
                }

                if (password != confirmPassword) {
                    message = "Паролі не співпадають"
                    return@Button
                }

                scope.launch {

                    isLoading = true

                    userPreferences.saveUser(
                        name = name,
                        email = email,
                        password = password
                    )

                    isLoading = false

                    message = "Реєстрація успішна!"

                    onBackToLogin()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Зареєструватися")
            }
        }

        if (message.isNotEmpty()) {

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        TextButton(
            onClick = onBackToLogin
        ) {
            Text("Вже є обліковий запис? Увійти")
        }
    }
}