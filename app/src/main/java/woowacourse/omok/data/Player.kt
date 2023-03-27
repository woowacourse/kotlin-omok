package woowacourse.omok.data

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Player(
    val id: Long = -1,
    val name: String,
    val win: Int = 0,
    val lose: Int = 0,
    val draw: Int = 0,

    @DrawableRes
    val profile: Int,
) : Serializable
