package woowacourse.omok.data

import androidx.annotation.DrawableRes

data class PlayerInfo(
    val name: String,
    val overallRecord: OverallRecord,

    @DrawableRes
    val profile: Int,
)
