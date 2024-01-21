package com.honorida.ui.components.pages.more.subPages.appSettings.subPages.appearanceSettings.components

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.representation.viewModels.AppThemesListViewModel
import com.honorida.ui.components.shared.extensions.secondaryItemAlpha
import com.honorida.ui.theme.HonoridaTheme
import com.honorida.ui.theme.ThemeType

@Composable
fun AppThemesList(
    onItemClick: (ThemeType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppThemesListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsState().value

    val appThemes = remember {
        ThemeType.entries.toList()
    }
    LazyRow(
        modifier = modifier
    ) {
        items(appThemes) { appTheme ->
            Column(
                modifier = Modifier
                    .width(114.dp)
                    .padding(top = 8.dp),
            ) {
                HonoridaTheme(
                    darkThemePreference = uiState.appearancePreferences.darkThemePreference,
                    themeType = appTheme
                ){
                    AppThemePreviewItem(
                        selected = uiState.appearancePreferences.currentTheme == appTheme,
                        onClick = {
                            onItemClick(appTheme)
                            (context as? Activity)?.let { ActivityCompat.recreate(it) }
                        },
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(appTheme.stringResourceId),
                    modifier = Modifier
                        .fillMaxWidth()
                        .secondaryItemAlpha(),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}