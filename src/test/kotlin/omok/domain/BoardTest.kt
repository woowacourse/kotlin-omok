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
        assertThat(board.isPlaceable(position)).isFalse
    }
}
