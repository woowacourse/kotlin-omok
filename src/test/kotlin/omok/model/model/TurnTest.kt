package omok.model.model

import omok.model.BlackTurn
import omok.model.Board
import omok.model.Point
import omok.model.Stone
import omok.model.StoneType
import omok.model.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TurnTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `흑돌 차례 다음은 백돌 차례이다`() {
        val turn = BlackTurn()
        val actual = turn.nextTurn(Point(0, 0), board)

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백돌 차례 다음은 흑돌 차례이다`() {
        val turn = WhiteTurn(Stone(StoneType.BLACK, Point(1, 0)))
        val actual = turn.nextTurn(Point(0, 0), board)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `현재 차례는 이전 차례에서 둔 돌의 위치를 알고있다`() {
        val turn = BlackTurn()
        val actual = turn.nextTurn(Point(0, 0), board)
        val expected = Stone(StoneType.BLACK, Point(0, 0))

        assertThat(actual.before).isEqualTo(expected)
    }
}
