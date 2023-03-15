package model.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokTest {
    @Test
    fun `Omok 에서 place 를 호출하면 예외가 발생한다`() {
        // given
        val state: State = Omok(Board.create())
        val location = Location(Coordination.from(1)!!, Coordination.from(1)!!)
        val board = Board.create()

        // when

        // then
        assertThrows<IllegalStateException>("") {
            state.place(location, board)
        }
    }
}
