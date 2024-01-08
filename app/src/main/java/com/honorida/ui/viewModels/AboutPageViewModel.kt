package com.honorida.ui.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.services.AppUpdater
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.navigation.toAppUpdate
import kotlinx.coroutines.launch

class AboutPageViewModel(
    private val appUpdater: AppUpdater
) : ViewModel() {

    fun checkForUpdates(
        context: Context,
        appVersion: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            appUpdater.checkForUpdates(
                context,
                appVersion
            ) {
                if (it.updateRequired) {
                    navController.navigate(
                        Routes.APP_UPDATE.toAppUpdate(it)
                    )
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.application_is_up_to_date),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}