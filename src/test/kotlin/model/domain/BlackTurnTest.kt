package model.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 BlackTurn 이 된다`() {
        // given
        val state: State = BlackTurn(Board.create())
        val location = Location(Coordination.from(1)!!, Coordination.from(1)!!)
        val board = Board.create()

        // when
        val actual = state.place(location, board)

        // then
        assertThat(actual is BlackTurn).isTrue
    }
}
