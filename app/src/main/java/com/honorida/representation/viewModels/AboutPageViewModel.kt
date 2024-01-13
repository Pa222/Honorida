package com.honorida.representation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.extensions.isPreReleaseVersion
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.ui.components.navigation.getAppUpdateUri
import kotlinx.coroutines.launch

class AboutPageViewModel(
    private val appUpdater: IAppUpdater
) : ViewModel() {

    fun checkForUpdates(
        context: Context,
        appVersion: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            appUpdater.checkForUpdates(
                context,
                appVersion,
                checkForPreRelease = appVersion.isPreReleaseVersion()
            ) {
                if (it.updateRequired) {
                    navController.navigate(getAppUpdateUri(it))
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