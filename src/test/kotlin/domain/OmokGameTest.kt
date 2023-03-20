package domain

import domain.listener.OmokListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑돌이 오목을 완성하면 게임이 종료되고, 흑돌이 승리한다`() {
        // given
        val black = (1..5).map { Stone(it, 0) }.toMutableList()
        val white = (1..5).map { Stone(it, 3) }.toMutableList()
        val turn = mutableListOf<Stone>()
        repeat(5) {
            turn.add(black.removeFirst())
            turn.add(white.removeFirst())
        }

        val omokGame = OmokGame(omokGameListener = FakeOmokListener(turn))

        // when
        val result = omokGame.runGame()

        // then
        assertThat(result).isEqualTo(State.BLACK)
    }

    @Test
    fun `백돌이 오목을 완성하면 게임이 종료되고, 백돌이 승리한다`() {
        // given
        val black = (1..4).map { Stone(it, it) }.toMutableList()
        black.add(Stone(14, 14))
        val white = (1..5).map { Stone(13, it) }.toMutableList()
        val turn = mutableListOf<Stone>()
        repeat(5) {
            turn.add(black.removeFirst())
            turn.add(white.removeFirst())
        }

        val omokGame = OmokGame(omokGameListener = FakeOmokListener(turn))

        // when
        val result = omokGame.runGame()

        // then
        assertThat(result).isEqualTo(State.WHITE)
    }

    private class FakeOmokListener(val turn: MutableList<Stone>) : OmokListener {
        var index = 0
        override fun onStoneRequest(): Stone {
            return turn[index++]
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
        }

        override fun onMoveFail() {
        }

        override fun onForbidden() {
        }

        override fun onFinish(state: State): State {
            return state
        }
    }
}
