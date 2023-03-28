package omok

import omok.judgement.RenjuJudgement
import omok.state.Fail
import omok.state.Turn
import omok.state.Win
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `해당 자리에 돌을 넣을 수 없으면 돌을 두는데 실패한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)
        val omokGame = OmokGame(board)

        // when
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.H, 2))
        val actual = omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 2))

        // then
        assertThat(actual).isEqualTo(Fail)
    }

    @Test
    fun `백돌이 돌을 넣고 오목이 아니면 흑돌의 차례가 된다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)
        val omokGame = OmokGame(board)

        // when
        val actual = omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 3))

        // then
        assertThat(actual).isEqualTo(Turn.Black)
    }

    @Test
    fun `흑돌이 돌을 넣고 오목이 아니면 백돌의 차례가 된다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)
        val omokGame = OmokGame(board)

        // when
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 2))
        val actual = omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.H, 3))

        // then
        assertThat(actual).isEqualTo(Turn.White)
    }

    @Test
    fun `백돌이 돌을 넣고 오목이면 백돌이 승리한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)
        val omokGame = OmokGame(board)

        // when
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 1))
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 2))
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 4))
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 5))
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.H, 6))

        val actual = omokGame.takeTurn(Turn.White, Position(HorizontalAxis.H, 3))

        // then
        assertThat(actual).isEqualTo(Win.White)
    }

    @Test
    fun `흑돌이 돌을 넣고 오목이면 흑돌이 승리한다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)
        val omokGame = OmokGame(board)

        // when
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.A, 1))
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.B, 2))
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.D, 4))
        omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.E, 5))
        omokGame.takeTurn(Turn.White, Position(HorizontalAxis.F, 6))

        val actual = omokGame.takeTurn(Turn.Black, Position(HorizontalAxis.C, 3))

        // then
        assertThat(actual).isEqualTo(Win.Black)
    }
}
