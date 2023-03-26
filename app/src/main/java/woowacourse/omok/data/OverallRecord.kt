package woowacourse.omok.data

import java.io.Serializable

data class OverallRecord(
    val win: Int = 0,
    val lose: Int = 0,
    val draw: Int = 0,
) : Serializable
