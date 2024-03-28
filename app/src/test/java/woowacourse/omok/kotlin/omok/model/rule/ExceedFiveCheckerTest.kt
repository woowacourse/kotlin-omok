package woowacourse.omok.kotlin.omok.model.rule

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.kotlin.omok.model.resetBoard
import woowacourse.omok.src.main.kotlin.omok.model.board.Board
import woowacourse.omok.src.main.kotlin.omok.model.position.Position
import woowacourse.omok.src.main.kotlin.omok.model.stone.BlackStone

class ExceedFiveCheckerTest {
    @BeforeEach
    fun setUp() {
        resetBoard()
    }

    @Test
    fun `장목 케이스`() {
        val blackStone = BlackStone
        blackStone.putStone(Position.of('C', 10))
        blackStone.putStone(Position.of('C', 11))
        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('C', 14))
        blackStone.putStone(Position.of('C', 15))

        val actual = Board.checkRenjuRule(2, 12)
        Assertions.assertThat(actual).isEqualTo(true)
    }
}
