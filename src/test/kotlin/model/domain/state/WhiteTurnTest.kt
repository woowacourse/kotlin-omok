package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `WhiteTurn 에서 BlackTurn 이 된다`() {
        // given
        val board = Board.create()
        val state: State = WhiteTurn(board)
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when
        val actual = state.place(location)

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `WhiteTurn 에서 WhiteOmok 이 된다`() {
        // given
        val board = Board.create()
        val state: State = WhiteTurn(board)

        state.apply {
            for (number in 1..4) {
                val location = Location(Coordination.from(number), Coordination.from(number))
                place(location)
            }
        }

        // when
        val location = Location(Coordination.from(5), Coordination.from(5))
        val actual = state.place(location)

        // then
        assertThat(actual is WhiteOmok).isTrue
    }
}
