package woowacourse.omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CellStateTest {
    @Test
    fun `WHITE일 때 BLACK을 반환한다`() {
        val state = CellState.WHITE

        val actual = state.reverseCellState()
        val expected = CellState.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `BLACK일 때 WHITE을 반환한다`() {
        val state = CellState.BLACK

        val actual = state.reverseCellState()
        val expected = CellState.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `EMPTY일 때 EMPTY를 반환한다`() {
        val state = CellState.EMPTY

        val actual = state.reverseCellState()
        val expected = CellState.EMPTY

        assertThat(actual).isEqualTo(expected)
    }
}
