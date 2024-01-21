package com.honorida.ui.components.pages.bookReader.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.R
import com.honorida.representation.viewModels.ReaderSettingsViewModel
import com.honorida.ui.components.shared.controls.IncDecControl

@Composable
fun ReaderSettings(
    open: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReaderSettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    DropdownMenu(
        expanded = open,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        IncDecControl(
            title = stringResource(R.string.font_size),
            min = 8,
            max = 72,
            step = if (uiState.readerSettings.fontSize >= 40) 2 else 1,
            value = uiState.readerSettings.fontSize,
            onChange = {
                viewModel.updateFontSize(it)
            }
        )
    }
}