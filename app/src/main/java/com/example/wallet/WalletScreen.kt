package com.example.wallet

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun WalletScreen(
    balance: Double,
    onWithdraw: (Float) -> Unit,
    onAddMoney: (Float) -> Unit
) {
    var withdrawInput by remember { mutableStateOf("") }
    var addInput by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text = "Balance",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "$${String.format("%.2f", balance)}",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Divider(Modifier.padding(vertical = 8.dp))

        // Retiro
        Text("Withdraw", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = withdrawInput,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    withdrawInput = newValue
                }
            },
            label = { Text("Amount to withdraw") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )
        Button(
            onClick = {
                val amount = withdrawInput.toFloatOrNull()
                when {
                    amount == null || amount <= 0 -> error = "Add a valid amount to withdraw"
                    amount > balance -> error = "Not enough funds"
                    else -> {
                        error = null
                        withdrawInput = ""
                        onWithdraw(amount)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Withdraw")
        }

        Divider(Modifier.padding(vertical = 8.dp))

        // Agregar dinero
        Text("Deposit", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = addInput,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    addInput = newValue
                }
            },
            label = { Text("Amount to deposit") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )
        Button(
            onClick = {
                val amount = addInput.toFloatOrNull()
                if (amount == null || amount <= 0) {
                    error = "Enter the amount to deposit"
                } else {
                    error = null
                    addInput = ""
                    onAddMoney(amount)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Deposit")
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
