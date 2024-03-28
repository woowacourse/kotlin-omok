import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.turn.BlackTurn
import omok.model.turn.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `오목판에 흑이 돌을 둔 다음 백이 두어야 한다`() {
        val whiteTurn = WhiteTurn(Either.Right(Board()))
        val actual = whiteTurn.proceed(Point(1, 1))
        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `오목판에 백이 돌을 둔 다음 흑이 두어야 한다`() {
        val blackTurn = BlackTurn(Either.Right(Board()))
        val actual = blackTurn.proceed(Point(9, 9))
        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `이전 턴에 어떤 곳에 돌을 놓았는지 알 수 있다`() {
        val board = Either.Right(Board())
        val point = Point(1, 1)
        val boardAfterPlace = (WhiteTurn(board).proceed(point).board as Either.Right).value
        val actual: Point = boardAfterPlace.previousStone()!!.point
        assertThat(actual).isEqualTo(point)
    }
}
