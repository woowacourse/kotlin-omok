package woowacourse.omok.data

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Player(
    val id: Int = -1,
    val name: String,
    val overallRecord: OverallRecord = OverallRecord(),

    @DrawableRes
    val profile: Int,
) : Serializable
