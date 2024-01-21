package com.honorida.domain.models

data class ProgressInfo(
    val max: Int,
    val current: Int,
    val indeterminate: Boolean = false
)