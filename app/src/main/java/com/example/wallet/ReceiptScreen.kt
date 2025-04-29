package com.example.wallet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ReceiptScreen(amount: Float, type: String, navController: NavController) {
    val actionLabel = when (type) {
        "deposit" -> "Deposit Successful"
        "withdraw" -> "Withdrawal Successful"
        else -> "Transaction"
    }

    val amountLabel = when (type) {
        "deposit" -> "Amount Deposited"
        "withdraw" -> "Amount Withdrawn"
        else -> "Amount"
    }

    val icon = when (type) {
        "deposit" -> "💰"
        "withdraw" -> "💸"
        else -> "✅"
    }

    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = showContent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.displayLarge
                )

                Text(
                    text = actionLabel,
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "$amountLabel: $${String.format("%.2f", amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        navController.popBackStack("wallet", inclusive = false)
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Wallet")
                }
            }
        }
    }
}
