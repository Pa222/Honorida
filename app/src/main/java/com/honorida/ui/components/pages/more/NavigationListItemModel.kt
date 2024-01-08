package com.honorida.ui.components.pages.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R

data class NavigationListItemModel(
    val icon: ImageVector? = null,
    val text: String,
    val description: String? = null,
    val onClick: () -> Unit = {  },
)

@Composable
fun NavigationListItem(
    item: NavigationListItemModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable {
                item.onClick()
            }
    ) {
        if (item.icon != null) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.text,
            )
        }
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = item.text,
            )
            if (item.description != null) {
                Text(
                    text = item.description,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MorePageListItemPreview() {
    NavigationListItem(
        item = NavigationListItemModel(
            icon = Icons.Outlined.Settings,
            text = stringResource(R.string.settings),
            description = stringResource(R.string.appearance_setting_description)
        )
    )
}