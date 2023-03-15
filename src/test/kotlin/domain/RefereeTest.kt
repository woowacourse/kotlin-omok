package domain

import domain.turn.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RefereeTest {
    @Test
    fun `가로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val state = State()
        (1..5).forEach { column ->
            state.move(Stone(1, column))
        }

        // when, then
        assertThat(referee.isWin(state)).isTrue
    }

    @Test
    fun `세로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val state = State()
        (1..5).forEach { row ->
            state.move(Stone(row, 1))
        }

        // when, then
        assertThat(referee.isWin(state)).isTrue
    }

    @Test
    fun `대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val state = State()
        (1..5).forEach { index ->
            state.move(Stone(index, index))
        }

        // when, then
        assertThat(referee.isWin(state)).isTrue
    }

    @Test
    fun `가로로 연속 4개 놓여있으면 승리가 아니다`() {
        // given
        val referee = Referee()
        val state = State()
        (1..4).forEach { column ->
            state.move(Stone(1, column))
        }

        // when, then
        assertThat(referee.isWin(state)).isFalse
    }
}
