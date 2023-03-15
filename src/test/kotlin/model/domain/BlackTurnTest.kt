package model.domain

import model.domain.state.BlackTurn
import model.domain.state.Omok
import model.domain.state.RetryBlackTurn
import model.domain.state.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 BlackTurn 이 된다`() {
        // given
        val state: State = BlackTurn()
        val location = Location(Coordination.from(1), Coordination.from(1))
        val board = Board.create()
        // when
        val actual = state.place(location, board)

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `BlackTurn 에서 RetryBlackTurn 이 된다`() {
        // given
        val state: State = BlackTurn()
        val location = Location(Coordination.from(1), Coordination.from(1))
        val board = Board.create()

        // when
        val actual = state.place(location, board).place(location, board)

        // then
        assertThat(actual is RetryBlackTurn).isTrue
    }

    @Test
    fun `BlackTurn 에서 Omok 이 된다`() {
        // given
        val state: State = BlackTurn()
        val board = Board.create()

        state.apply {
            for (number in 1..4) {
                val location = Location(Coordination.from(number), Coordination.from(number))
                place(location, board)
            }
        }

        // when
        val location = Location(Coordination.from(5), Coordination.from(5))

        val actual = state.place(location, board)

        println(board.system)
        // then
        assertThat(actual is Omok).isTrue
    }

    @Test
    fun `BlackTurn 에서 Omok 이 아닌 경우`() {
        // given
        val state: State = BlackTurn()
        val board = Board.create()

        state.apply {
            for (number in 1..4) {
                val location = Location(Coordination.from(number), Coordination.from(number))
                place(location, board)
            }
        }

        // when
        val location = Location(Coordination.from(5), Coordination.from(8))

        val actual = state.place(location, board)

        println(board.system)
        // then
        assertThat(actual is Omok).isFalse
    }
}
