package com.honorida.domain.extensions

fun String.replaceValues(params: Map<String, String>): String {
    val result = StringBuilder(this)

    for ((key, value) in params) {
        val placeholder = "{$key}"
        val startIndex = result.indexOf(placeholder)
        val endIndex = startIndex + placeholder.length

        if (startIndex != -1) {
            result.replace(startIndex, endIndex, value)
        }
    }

    return result.toString()
}