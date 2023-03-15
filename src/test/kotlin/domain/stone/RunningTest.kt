package domain.stone

import domain.state.Running
import domain.state.State
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunningTest {

    @Test
    fun `바둑알이 놓으려는 곳에 이미 놓여진 경우 False를 반환`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(2, 1)!!, StoneType.BLACK)

        val running: Running = TestRunning(stones, OmokRule())
        assertThat(running.isValidPut(stone)).isFalse()
    }

    @Test
    fun `바둑알이 놓으려는 곳에 없는 경우 True를 반환`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(3, 1)!!, StoneType.BLACK)

        val running: Running = TestRunning(stones, OmokRule())
        assertThat(running.isValidPut(stone)).isTrue()
    }
}

class TestRunning(stones: Stones, omokRule: OmokRule) : Running(stones, omokRule) {
    override fun put(stone: Stone): State {
        TODO("Not yet implemented")
    }
}
