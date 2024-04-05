package woowacourse.omok.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.omok.database.omokturn.OmokTurnEntity
import woowacourse.omok.database.omokturn.toStonePosition
import woowacourse.omok.domain.model.Stone

class OmokTurnEntityTest {
    @Test
    fun omokTurnToStonePosition() {
        val omokTurnEntity = OmokTurnEntity(1, 1, "BLACK")
        val stonePosition = omokTurnEntity.toStonePosition()
        assertEquals(stonePosition.position.row, 1)
        assertEquals(stonePosition.position.col, 1)
        assertEquals(stonePosition.stone, Stone.BLACK)
    }
}
