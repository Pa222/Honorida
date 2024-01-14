package com.honorida.domain.constants

sealed class Extras(
    val key: String
) {
    data object UpdateRequired: Extras("updateRequired")
    data object ReleaseId: Extras("releaseId")
    data object OpenAppUpdatePage: Extras("openAppUpdatePage")
    data object NotificationType: Extras("notificationType")
    data object FileUri: Extras("fileUri")
}