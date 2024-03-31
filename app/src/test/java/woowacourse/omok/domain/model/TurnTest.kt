package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TurnTest {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter

    @BeforeEach
    fun setUp() {
        board = Board(15)
        ruleAdapter = RuleAdapter(board)
    }

    @Test
    fun `흑돌 차례 다음은 백돌 차례이다`() {
        val turn = BlackTurn()
        val actual = turn.nextTurn(Stone(StoneType.BLACK, Point(0, 0)), ruleAdapter)

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백돌 차례 다음은 흑돌 차례이다`() {
        val turn = WhiteTurn()
        val actual = turn.nextTurn(Stone(StoneType.WHITE, Point(0, 0)), ruleAdapter)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
