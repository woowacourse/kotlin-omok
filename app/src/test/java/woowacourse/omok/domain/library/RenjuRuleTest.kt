package woowacourse.omok.domain.library

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.StoneType

class RenjuRuleTest {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter

    @BeforeEach
    fun setUp() {
        board = Board()
        ruleAdapter = RuleAdapter(board)
    }

    @Test
    fun `붙어있는 '3-3' 일 경우 착수가 금지된다`() {
        board.putStoneOnTable(Point(3, 11), StoneType.BLACK)
        board.putStoneOnTable(Point(4, 13), StoneType.BLACK)
        board.putStoneOnTable(Point(4, 12), StoneType.BLACK)
        board.putStoneOnTable(Point(5, 11), StoneType.BLACK)

        assertThat(ruleAdapter.checkForbidden(Point(4, 11), StoneType.BLACK)).isTrue()
    }

    @Test
    fun `떨어져있는 '3-3'일 경우 착수가 금지된다`() {
        board.putStoneOnTable(Point(2, 5), StoneType.BLACK)
        board.putStoneOnTable(Point(3, 4), StoneType.BLACK)
        board.putStoneOnTable(Point(5, 4), StoneType.BLACK)
        board.putStoneOnTable(Point(5, 5), StoneType.BLACK)

        assertThat(ruleAdapter.checkForbidden(Point(5, 2), StoneType.BLACK)).isTrue()
    }

    @Test
    fun `장목일 경우 착수가 금지된다`() {
        board.putStoneOnTable(Point(2, 8), StoneType.BLACK)
        board.putStoneOnTable(Point(2, 7), StoneType.BLACK)
        board.putStoneOnTable(Point(2, 6), StoneType.BLACK)
        board.putStoneOnTable(Point(2, 4), StoneType.BLACK)
        board.putStoneOnTable(Point(2, 3), StoneType.BLACK)

        assertThat(ruleAdapter.checkForbidden(Point(2, 5), StoneType.BLACK)).isTrue()
    }

    @Test
    fun `붙어있는 '4-4' 일 경우 착수가 금지된다`() {
        board.putStoneOnTable(Point(3, 7), StoneType.BLACK)
        board.putStoneOnTable(Point(4, 7), StoneType.BLACK)
        board.putStoneOnTable(Point(7, 7), StoneType.BLACK)
        board.putStoneOnTable(Point(9, 7), StoneType.BLACK)
        board.putStoneOnTable(Point(10, 7), StoneType.BLACK)

        assertThat(ruleAdapter.checkForbidden(Point(6, 7), StoneType.BLACK)).isTrue()
    }

    @Test
    fun `떨어져있는 '4-4'일 경우 착수가 금지된다`() {
        board.putStoneOnTable(Point(3, 12), StoneType.BLACK)
        board.putStoneOnTable(Point(4, 12), StoneType.BLACK)
        board.putStoneOnTable(Point(7, 12), StoneType.BLACK)
        board.putStoneOnTable(Point(9, 12), StoneType.BLACK)
        board.putStoneOnTable(Point(10, 12), StoneType.BLACK)

        assertThat(ruleAdapter.checkForbidden(Point(6, 12), StoneType.BLACK)).isTrue()
    }
}
