package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.database.OmokTurn
import woowacourse.omok.domain.model.toOmokTurn

class StonePositionTest {
    @Test
    fun stonePositionToOmokTurn() {
        val stonePosition = StonePosition(Position(1, 1), Stone.BLACK)
        val actual = stonePosition.toOmokTurn()
        assertThat(actual).isEqualTo(OmokTurn(1, 1, "BLACK"))
    }
}
