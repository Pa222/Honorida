package com.honorida.representation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.ui.components.navigation.getAppUpdateUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutPageViewModel @Inject constructor(
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