package domain

import domain.listener.Listener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import view.OutputView

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

        val omokGame = OmokGame(listener = fakeListener(turn))
        omokGame.runGame()

        // when
        val result = omokGame.omokBoard.isVictory(State.BLACK)

        // then
        assertThat(result).isTrue
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

        val omokGame = OmokGame(listener = fakeListener(turn))
        omokGame.runGame()

        // when
        val result = omokGame.omokBoard.isVictory(State.WHITE)

        // then
        assertThat(result).isTrue
    }

    private class fakeListener(val turn: MutableList<Stone>) : Listener {
        var index = 0
        override fun onStoneRequest(): Stone {
            return turn[index++]
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
            OutputView().printOmokState(omokBoard, state, stone)
        }

        override fun onMoveFail() {
            OutputView().printDuplicate()
        }

        override fun onForbidden() {
            OutputView().printForbidden()
        }

        override fun onFinish(state: State) {
            OutputView().printWinner(state)
        }
    }
}
