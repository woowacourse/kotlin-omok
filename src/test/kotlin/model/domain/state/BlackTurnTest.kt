package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import model.domain.tools.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 WhiteTurn 이 된다`() {
        // given
        val board = Board.from(15)
        val state: State = BlackTurn(board)
        val location = Location(Coordination.from(1), Coordination.from(1))

        // when
        val actual = state.place(location)

        // then
        assertThat(actual is WhiteTurn).isTrue
    }

    @Test
    fun `BlackTurn 에서 오목이 발생하면 종료되고 바둑돌은 검정색이다`() {
        // given
        val board = Board.from(15)
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
        assertAll(
            { assertThat(actual is End).isTrue },
            { assertThat(actual.stone).isEqualTo(Stone.BLACK) },
        )
    }

    @Test
    fun `BlackTurn 은 금수 룰이 적용되어 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15)
        val state: State = BlackTurn(board)

        state.apply {
            place(Location(Coordination.from(5), Coordination.from(6)))
            place(Location(Coordination.from(5), Coordination.from(7)))
            place(Location(Coordination.from(6), Coordination.from(5)))
            place(Location(Coordination.from(7), Coordination.from(5)))
        }

        // when
        val location = Location(Coordination.from(5), Coordination.from(5))
        val actual = state.place(location)

        // then
        assertThat(actual is BlackTurn).isTrue
    }
}
