package model.domain

import model.domain.state.BlackTurn
import model.domain.state.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 BlackTurn 이 된다`() {
        // given
        val state: State = BlackTurn()
        val location = Location(Coordination.from(1)!!, Coordination.from(1)!!)
        val board = Board.create()

        // when
        val actual = state.place(location, board)

        // then
        assertThat(actual is BlackTurn).isTrue
    }
}
