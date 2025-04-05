package woowacourse.omok.database

import woowacourse.omok.model.StoneColor

data class SavedStone(
    val x: Int,
    val y: Int,
    val color: StoneColor,
)
