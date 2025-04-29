package com.example.wallet

import androidx.navigation.navArgument
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.wallet.ui.theme.WalletTheme

class WalletActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var balance by remember { mutableStateOf(1000.0) }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "wallet") {
        composable("wallet") {
            WalletScreen(
                balance = balance,
                onWithdraw = { amount ->
                    balance -= amount
                    navController.navigate("receipt/$amount/withdraw")
                },
                onAddMoney = { amount ->
                    balance += amount
                    navController.navigate("receipt/$amount/deposit")
                }
            )
        }
        composable(
            "receipt/{amount}/{type}",
            arguments = listOf(
                navArgument("amount") { type = NavType.FloatType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getFloat("amount") ?: 0f
            val type = backStackEntry.arguments?.getString("type") ?: "unknown"
            ReceiptScreen(amount, type, navController)
        }
    }
}
