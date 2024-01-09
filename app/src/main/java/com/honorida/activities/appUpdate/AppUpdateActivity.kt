package com.honorida.activities.appUpdate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.honorida.HonoridaApp
import com.honorida.activities.appUpdate.ui.appUpdate.AppUpdatePage
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.domain.services.Extras
import com.honorida.activities.main.ui.theme.HonoridaTheme

class AppUpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val extras = intent.extras;
            val context = LocalContext.current

            if (extras == null) {
                HonoridaApp.appModule.activitiesManager.startMainActivity(context)
            } else {
                val darkThemePreference = HonoridaApp.appModule.protoDataStore
                    .appearancePreferences.data.collectAsState(
                        initial = AppearancePreferences()
                    ).value.darkThemePreference

                HonoridaTheme(
                    darkThemePreference = darkThemePreference
                ) {
                    Scaffold { innerPadding ->
                        AppUpdatePage(
                            updateUrl = extras.getString(Extras.UpdateUrl.key, ""),
                            latestAppVersion = extras.getString(Extras.LatestAppVersion.key, ""),
                            releaseUrl = extras.getString(Extras.ReleaseUrl.key, ""),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}