package model.domain.state

import model.domain.tools.Board
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

        // when
        val actual = state.place(Location(1, 1))

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
                val location = Location(number, number)
                place(location)
            }
        }

        // when
        val actual = state.place(Location(5, 5))

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
            place(Location(5, 6))
            place(Location(5, 7))
            place(Location(6, 5))
            place(Location(7, 5))
        }

        // when
        val actual = state.place(Location(5, 5))

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `BlackTurn 은 장목 룰이 적용되어 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15)
        val state: State = BlackTurn(board)

        state.apply {
            place(Location(0, 1))
            place(Location(0, 2))
            place(Location(0, 4))
            place(Location(0, 5))
            place(Location(0, 6))
        }

        // when
        val location = Location(0, 3)
        val actual = state.place(location)

        // then
        assertThat(actual is BlackTurn).isTrue
    }
}
