package woowacourse.omok.model.state

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.model.initBoard

class FinishedTest {
    private val emptyBoard: Board = initBoard()

    @Test
    fun `종료 상태에서는 돌을 더 둘 수 없다`() {
        val finished = Finished(StonePosition(Position(1, 1), Stone.BLACK))
        assertThrows<IllegalStateException> {
            finished.place(emptyBoard, Position(1, 1))
        }
    }
}
