package woowacourse.omok.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.database.OmokTurn
import woowacourse.omok.domain.model.database.toStonePosition

class OmokTurnTest {
    @Test
    fun omokTurnToStonePosition() {
        val omokTurn = OmokTurn(1, 1, "BLACK")
        val stonePosition = omokTurn.toStonePosition()
        assertEquals(stonePosition.position.row, 1)
        assertEquals(stonePosition.position.col, 1)
        assertEquals(stonePosition.stone, Stone.BLACK)
    }
}
