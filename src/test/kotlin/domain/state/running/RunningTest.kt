package domain.state.running

import domain.state.State
import domain.stone.Board
import domain.stone.StonePosition
import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunningTest {

    @Test
    fun `Running에서 getWinner을 호출시 StoneType이 EMPTY로 반환된다`() {
        val board: Board = Board()
        val running: Running = TestRunning(board)

        assertThat(running.getWinner()).isEqualTo(StoneType.EMPTY)
    }
}

class TestRunning(board: Board) : Running(board) {
    override fun next(stonePosition: StonePosition): State {
        TODO("Not yet implemented")
    }
}
