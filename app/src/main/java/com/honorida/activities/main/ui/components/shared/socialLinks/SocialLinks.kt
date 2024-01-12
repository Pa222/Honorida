package com.honorida.activities.main.ui.components.shared.socialLinks

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honorida.BuildConfig
import com.honorida.R

@Composable
fun SocialLinks(
    modifier: Modifier = Modifier
) {
    val icons =
        listOf(
            SocialLink(
                painterResource(R.drawable.icon_github),
                BuildConfig.REPO_URL
            )
        )
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Row(
        modifier = modifier
    ) {
        icons.forEach {
            Icon(
                painter = it.icon,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        try {
                            uriHandler.openUri(it.link)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast
                                .makeText(
                                    context,
                                    context.getString(R.string.failed_to_open_link),
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                    .padding(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SocialLinksPreview() {
    SocialLinks()
}