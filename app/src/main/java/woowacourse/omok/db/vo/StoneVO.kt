package woowacourse.omok.db.vo

import domain.stone.Color
import domain.stone.Position

data class StoneVO(
    val id: Int,
    val stoneColor: Color,
    val position: Position,
    val roomNum: Int
)
