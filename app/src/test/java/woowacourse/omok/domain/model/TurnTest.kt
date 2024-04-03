package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TurnTest {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter

    @BeforeEach
    fun setUp() {
        board = Board()
        ruleAdapter = RuleAdapter(board)
    }

    @Test
    fun `흑돌 차례 다음은 백돌 차례이다`() {
        val turn = BlackTurn()
        val actual = turn.nextTurn(false)

        assertThat(actual is WhiteTurn).isTrue()
    }

    @Test
    fun `백돌 차례 다음은 흑돌 차례이다`() {
        val turn = WhiteTurn()
        val actual = turn.nextTurn(false)

        assertThat(actual is BlackTurn).isTrue()
    }

    @Test
    fun `승리 조건을 달성하면 게임이 종료된다`() {
        val turn = BlackTurn()
        val actual = turn.nextTurn(true)

        assertThat(actual is FinishedTurn).isTrue()
    }
}
