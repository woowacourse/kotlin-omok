package omok.domain

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    fun `플레이어가 돌을 두면 더 이상 그 위치에 돌을 둘 수 없다`() {
        // given
        val whitePlayer = Player()
        val blackPlayer = Player()
        val board = Board(whitePlayer, blackPlayer)
        val position = Position(HorizontalAxis.H, 3)

        // when
        board.putStone(position)

        // then
        assertThat(board.isBlackPlaceable(position)).isFalse
    }

    @Test
    fun `장목이면 돌을 놓을 수 있다`() {
        // given
        val blackPlayer = Player()
        val whitePlayer = Player()
        val position = Position(HorizontalAxis.C, 13)
        val board = Board(blackPlayer, whitePlayer)

        // when
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 15)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 14)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 12)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 11)))
        blackPlayer.put(BlackStone(Position(HorizontalAxis.C, 10)))

        // then
        assertThat(board.isBlackPlaceable(position)).isTrue
    }
}
