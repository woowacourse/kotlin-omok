package model.domain

import model.domain.state.Omok
import model.domain.state.State
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokTest {
    @Test
    fun `Omok 에서 place 를 호출하면 예외가 발생한다`() {
        // given
        val state: State = Omok()
        val location = Location(Coordination.from(1), Coordination.from(1))
        val board = Board.create()

        // when

        // then
        assertThrows<IllegalStateException>("") {
            state.place(location, board)
        }
    }
}
