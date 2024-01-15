package com.honorida.representation.uiStates

//data class StorageInfo(
//
//)

data class StorageSettingsUiState(
    val selectedStorage: String = "",
    val freeSpace: Long = -1L,
    val totalSpace: Long = -1L,
)