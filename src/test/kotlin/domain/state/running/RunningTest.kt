package domain.state.running

import domain.state.State
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunningTest {

    @Test
    fun `바둑알이 놓으려는 곳에 이미 놓여진 경우 False를 반환`() {
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(2, 1)!!, StoneType.BLACK)

        val running: Running = TestRunning(board)
        assertThat(running.isValidPut(stone.position)).isFalse
    }

    @Test
    fun `바둑알이 놓으려는 곳에 없는 경우 True를 반환`() {
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(3, 1)!!, StoneType.BLACK)

        val running: Running = TestRunning(board)
        assertThat(running.isValidPut(stone.position)).isTrue()
    }
}

class TestRunning(board: Board) : Running(board) {
    override fun next(stonePosition: StonePosition): State {
        TODO("Not yet implemented")
    }
}
