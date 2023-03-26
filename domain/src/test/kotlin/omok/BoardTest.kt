package omok

import omok.judgement.RenjuJudgement
import omok.state.Turn
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `플레이어가 돌을 두면 더 이상 그 위치에 돌을 둘 수 없다`() {
        val position = Position(HorizontalAxis.H, 3)
        val whitePlayer = Player()
        val blackPlayer = Player(listOf(Stone(position)))
        val board = Board(RenjuJudgement(), whitePlayer, blackPlayer)

        // then
        assertThat(board.isPlaceable(Turn.Black, position)).isFalse
    }

    @Test
    fun `장목이면 돌을 놓을 수 있다`() {
        // given
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.C, 13)

        // when
        val blackPlayer = Player(
            listOf(
                Stone(Position(HorizontalAxis.C, 15)),
                Stone(Position(HorizontalAxis.C, 14)),
                Stone(Position(HorizontalAxis.C, 12)),
                Stone(Position(HorizontalAxis.C, 11)),
                Stone(Position(HorizontalAxis.C, 10))
            )
        )
        val board = Board(RenjuJudgement(), blackPlayer, whitePlayer)

        // then
        assertThat(board.isPlaceable(Turn.Black, position)).isTrue
    }
}
