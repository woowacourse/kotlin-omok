package model.domain

import model.domain.state.State
import model.domain.state.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `WhiteTurn 에서 WhiteTurn 이 된다`() {
        // given
        val state: State = WhiteTurn()
        val location = Location(Coordination.from(1)!!, Coordination.from(1)!!)
        val board = Board.create()

        // when
        val actual = state.place(location, board)

        // then
        assertThat(actual is WhiteTurn).isTrue
    }
}
