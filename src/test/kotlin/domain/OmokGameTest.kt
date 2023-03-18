package domain

import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑돌이 오목을 완성하면 게임이 종료된다`() {
        val omokGame = OmokGame(omokGameListener = listener())
        omokGame.runGame()
    }

    private fun listener() = object : Listener {
        val black = (1..5).map { Stone(it, 0) }.toMutableList()
        val white = (1..5).map { Stone(it, 3) }.toMutableList()
        val turn = mutableListOf<Stone>()
        var index = 0

        init {
            repeat(5) {
                turn.add(black.removeFirst())
                turn.add(white.removeFirst())
            }
        }

        override fun onStoneRequest(): Stone {
            return turn[index++]
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

        override fun onMoveFail() {}

        override fun onForbidden() {}

        override fun onFinish(state: State) {}
    }
}
