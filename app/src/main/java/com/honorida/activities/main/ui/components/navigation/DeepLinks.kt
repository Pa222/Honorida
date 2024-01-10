package com.honorida.activities.main.ui.components.navigation

import android.content.Intent
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import java.net.URLEncoder

private const val rootUri = "https://app.honorida.com"

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
        uriPattern = "${rootUri}/${Routes.APP_UPDATE.route}/{${Extras.UpdateUrl.key}}" +
                "/{${Extras.LatestAppVersion.key}}/{${Extras.ReleaseUrl.key}}"
        action = Intent.ACTION_VIEW
    })

    companion object {
        fun toAppUpdate(updateInfo: CheckUpdateResponse) : String {
            return buildString {
                append("${rootUri}/${Routes.APP_UPDATE.route}")
                append('/')
                append(URLEncoder.encode(updateInfo.updateUrl, "utf-8"))
                append('/')
                append(updateInfo.latestAppVersion)
                append('/')
                append(URLEncoder.encode(updateInfo.releaseUrl, "utf-8"))
            }
        }
    }
}