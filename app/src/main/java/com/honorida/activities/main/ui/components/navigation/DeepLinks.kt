package com.honorida.activities.main.ui.components.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.honorida.activities.main.MainActivity
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.extensions.replaceValues

private const val rootUri = "honorida://honorida.app"

sealed class DeepLinks(
    val link: NavDeepLink
) {
    data object Library: DeepLinks(navDeepLink {
        uriPattern = "${rootUri}/${Routes.LIBRARY.route}"
        action = Intent.ACTION_VIEW
    })
    data object History: DeepLinks(navDeepLink {
        uriPattern = "${rootUri}/${Routes.HISTORY.route}"
        action = Intent.ACTION_VIEW
    })

    data object AppUpdate: DeepLinks(navDeepLink {
        uriPattern = "${rootUri}/${Routes.APP_UPDATE.route}"
        action = Intent.ACTION_VIEW
    }) {
        fun getIntent(
            context: Context,
            updateInfo: CheckUpdateResponse
        ) :Intent {
            return Intent(
                Intent.ACTION_VIEW,
                DeepLinks.getAppUpdateUri(updateInfo),
                context,
                MainActivity::class.java
            )
        }
    }

    companion object {
        fun getAppUpdateUri(updateInfo: CheckUpdateResponse) : Uri {
            return AppUpdate.link.uriPattern!!.replaceValues(
                params = mapOf(
                    Extras.ReleaseId.key to updateInfo.releaseId.toString(),
                )
            ).toUri()
        }
    }
}