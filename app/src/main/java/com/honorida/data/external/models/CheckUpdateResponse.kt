package com.honorida.data.external.models

import android.os.Bundle
import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import com.honorida.domain.constants.Extras

@Keep
data class CheckUpdateResponse (
    @JsonProperty("updateRequired") val updateRequired: Boolean,
    @JsonProperty("releaseId") val releaseId: Int,
) {

    companion object {
        fun fromExtras(extras: Bundle) : CheckUpdateResponse? {
            val releaseIdAsString = extras.getString(Extras.ReleaseId.key) ?: return null
            return CheckUpdateResponse(
                updateRequired = extras.getBoolean(Extras.UpdateRequired.key),
                releaseId = releaseIdAsString.toInt()
            )
        }
    }
}
