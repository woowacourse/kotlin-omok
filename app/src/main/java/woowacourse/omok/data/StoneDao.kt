package woowacourse.omok.data

import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class StoneDao private constructor(
    val stoneColor: StoneColor,
    val row: Row,
    val col: Col,
) {
    companion object {
        fun valueOf(stone: Stone): StoneDao {
            return StoneDao(
                stone.color,
                stone.position.row,
                stone.position.col,
            )
        }
    }
}
