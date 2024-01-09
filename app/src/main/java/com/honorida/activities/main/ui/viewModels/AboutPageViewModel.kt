package com.honorida.activities.main.ui.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.HonoridaApp.Companion.appModule
import com.honorida.R
import com.honorida.domain.services.AppUpdater
import kotlinx.coroutines.launch

class AboutPageViewModel(
    private val appUpdater: AppUpdater
) : ViewModel() {

    fun checkForUpdates(
        context: Context,
        appVersion: String,
    ) {
        viewModelScope.launch {
            appUpdater.checkForUpdates(
                context,
                appVersion
            ) {
                if (it.updateRequired) {
                    appModule.activitiesManager.startAppUpdateActivity(
                        context = context,
                        it
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