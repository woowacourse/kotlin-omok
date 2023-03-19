package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 WhiteTurn 이 된다`() {
        // given
        val board = Board.create()
        val state: State = BlackTurn(board)
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when
        val actual = state.place(location)

        // then
        assertThat(actual is WhiteTurn).isTrue
    }

    @Test
    fun `BlackTurn 에서 BlackOmok 이 된다`() {
        // given
        val board = Board.create()
        val state: State = BlackTurn(board)

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
        assertThat(actual is BlackOmok).isTrue
    }
}
