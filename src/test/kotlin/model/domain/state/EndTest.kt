package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import model.domain.tools.Stone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EndTest {

    @Test
    fun `End 에서 place 를 호출하면 예외가 발생한다`() {
        // given
        val board = Board.from(15)
        val state: State = End(board, Stone.BLACK)
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when

        // then
        assertThrows<IllegalStateException>("") {
            state.place(location)
        }
    }
}
