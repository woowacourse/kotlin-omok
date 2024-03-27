package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.StoneType

class BoardTest {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter

    @BeforeEach
    fun setUp() {
        board = Board(15)
        ruleAdapter = RuleAdapter(board)
    }

    @Test
    fun `보드에 돌 착수 확인`() {
        val point = Point(3, 8)

        board.putStone(point, BlackTurn(), ruleAdapter)

        assertThat(board.getBoardPoint(Point(3, 8)) == StoneType.BLACK).isTrue
    }

    @Test
    fun `보드는 이전 차례에서 둔 돌의 위치를 알고있다`() {
        board.putStone(
            Point(0, 0),
            BlackTurn(),
            ruleAdapter
        )

        val actual = board.beforePoint

        assertThat(actual).isEqualTo(Point(0, 0))
    }
}
