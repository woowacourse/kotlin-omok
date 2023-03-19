package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhtieOmokTest {

    @Test
    fun `WhiteOmok 의 Stone 은 White 이다`() {
        // given
        val board = Board.create()
        val state: State = WhiteOmok(board)

        // when
        val actual = state.stone
        // then
        assertThat(actual).isEqualTo(Stone.WHITE)
    }
}
