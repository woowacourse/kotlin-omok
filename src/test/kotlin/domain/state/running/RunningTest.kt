package domain.state.running

import domain.state.State
import domain.stone.Board
import domain.stone.StonePosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunningTest {

    @Test
    fun `Running에서 getWinner을 호출시 null이 반환된다`() {
        val running: Running = TestRunning()

        assertThat(running.getWinner()).isNull()
    }
}

class TestRunning() : Running() {
    override fun next(board: Board, stonePosition: StonePosition): State {
        TODO("Not yet implemented")
    }
}
