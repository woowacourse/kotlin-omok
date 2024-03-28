package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        board.putStone(Stone(StoneType.BLACK, Point(3, 8)), BlackTurn(), ruleAdapter)

        assertThat(board.getBoardPoint(Point(3, 8)) == StoneType.BLACK).isTrue
    }

    @Test
    fun `보드는 이전 차례에서 둔 돌의 위치를 알고있다`() {
        board.putStone(
            Stone(StoneType.BLACK, Point(0, 0)),
            BlackTurn(),
            ruleAdapter
        )

        val actual = board.beforeStone

        assertThat(actual).isEqualTo(Stone(StoneType.BLACK, Point(0, 0)))
    }
}
