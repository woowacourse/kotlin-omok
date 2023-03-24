package woowacourse.omok.data

import androidx.annotation.DrawableRes

data class Player(
    val id: Int? = null,
    val name: String,
    val overallRecord: OverallRecord = OverallRecord(),

    @DrawableRes
    val profile: Int,
)
