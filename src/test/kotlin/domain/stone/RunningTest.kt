package domain.stone

import domain.state.Running
import domain.state.State
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.point.Point

class RunningTest {

    @Test
    fun `게임 진행 중에 우승돌을 찾으려고 하면 에러가 발생한다`() {
        val board: Board = Board()
        board.putStone(Stone(Point(2, 1), StoneType.BLACK))

        val running: Running = TestRunning()

        assertThrows<IllegalStateException> {
            running.getWinner()
        }
    }
}

class TestRunning() : Running() {
    override fun put(stone: Stone): State {
        TODO("Not yet implemented")
    }
}
