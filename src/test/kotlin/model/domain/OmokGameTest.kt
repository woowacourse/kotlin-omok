package model.domain

import model.domain.state.BlackTurn
import model.domain.tools.Board
import model.domain.tools.Dot
import model.domain.tools.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `돌을 놓을 수 없으면 상태가 바뀌지 않는다 `() {
        // given
        val dots = mutableListOf(Dot(1, 1), Dot(1, 1))
        fun getTestDot(stone: Stone): Dot = dots.removeFirst()
        val state = BlackTurn(Board.create())
        val omokGame = OmokGame()

        // when
        omokGame.playNextTurn(state, ::getTestDot)
        val actual = omokGame.playNextTurn(state, ::getTestDot)
        // then
        assertThat(actual).isEqualTo(state)
    }

    @Test
    fun `돌을 놓을 수 있으면 상태가 바뀐다`() {
// given
        val dots = mutableListOf(Dot(1, 1), Dot(2, 2))
        fun getTestDot(stone: Stone): Dot = dots.removeFirst()
        val state = BlackTurn(Board.create())
        val omokGame = OmokGame()

        // when
        omokGame.playNextTurn(state, ::getTestDot)
        val actual = omokGame.playNextTurn(state, ::getTestDot)

        // then
        assertThat(actual).isNotEqualTo(state)
    }
}
