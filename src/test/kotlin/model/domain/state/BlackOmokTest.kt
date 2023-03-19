package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackOmokTest {

    @Test
    fun `BlackOmok 의 Stone 은 BLACK 이다`() {
        // given
        val board = Board.create()
        val state: State = BlackOmok(board)

        // when
        val actual = state.stone
        // then
        assertThat(actual).isEqualTo(Stone.BLACK)
    }
}
