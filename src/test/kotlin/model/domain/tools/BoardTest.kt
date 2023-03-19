package model.domain.tools

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `Board 의 Location 에 돌이 있으면 놓을 수 없다`() {
        // given
        val board = Board.create()
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when
        board.placeStone(location, Stone.BLACK)

        // then
        assertThat(board.canPlace(location)).isFalse
    }

    @Test
    fun `Board 의 Location 에 돌이 없으면 놓을 수 있다`() {
        // given
        val board = Board.create()
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when

        // then
        assertThat(board.canPlace(location)).isTrue
    }
}
