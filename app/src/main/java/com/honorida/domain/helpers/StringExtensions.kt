package com.honorida.domain.helpers

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

fun String?.asBoolean(): Boolean {
    if (this == null) {
        return false
    }
    return this.equals("true", ignoreCase = true)
}

fun String.isPreReleaseVersion(): Boolean {
    return this.contains("alpha", ignoreCase = true)
}

fun String.getFileExtension(): String? {
    if (!this.contains('.')) {
        return null
    }
    return this.subSequence(this.lastIndexOf('.') + 1, this.length).toString()
}

fun String.getFileNameFromUrl(): String? {
    if (!this.contains('/')) {
        return null
    }
    return this.subSequence(this.lastIndexOf('/') + 1, this.length).toString()
}