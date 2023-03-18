package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import view.OutputView

class OmokGameTest {
    @Test
    fun `흑돌이 오목을 완성하면 게임이 종료되고, 흑돌이 승리한다`() {
        val omokGame = OmokGame()
        val black = (1..5).map { Stone(it, 0) }.toMutableList()
        val white = (1..5).map { Stone(it, 3) }.toMutableList()
        val turn = mutableListOf<Stone>()
        repeat(5) {
            turn.add(black.removeFirst())
            turn.add(white.removeFirst())
        }
        var index = 0
        omokGame.runGame({ turn[index++] }, OutputView::printOmokState, {}, {}, {})

        val result = omokGame.omokBoard.isVictory(State.BLACK)

        assertThat(result).isTrue
    }

    @Test
    fun `백돌이 오목을 완성하면 게임이 종료되고, 백돌이 승리한다`() {
        val omokGame = OmokGame()
        val black = (1..4).map { Stone(it, it) }.toMutableList()
        black.add(Stone(14, 14))
        val white = (1..5).map { Stone(13, it) }.toMutableList()
        val turn = mutableListOf<Stone>()
        repeat(5) {
            turn.add(black.removeFirst())
            turn.add(white.removeFirst())
        }
        var index = 0
        omokGame.runGame({ turn[index++] }, OutputView::printOmokState, {}, {}, {})

        val result = omokGame.omokBoard.isVictory(State.WHITE)

        assertThat(result).isTrue
    }

    private fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}
}
