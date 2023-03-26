import domain.judgement.RenjuRule
import domain.stone.Color
import domain.turn.RunningBoardState
import domain.turn.StartBoardState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StartBoardStateTest {

    @Test
    fun `첫 돌로 검은 돌을 놓지 않으면 에러가 발생한다`() {
        val startBoardState = StartBoardState(RenjuRule())
        assertThrows<IllegalArgumentException> {
            startBoardState.putStone(
                Stone(
                    3,
                    5,
                    Color.WHITE
                )
            )
        }
    }

    @Test
    fun `돌을 추가하면 RunningBoardState를 반환한다`() {
        val startBoardState = StartBoardState(RenjuRule())
        val result = startBoardState.putStone(Stone(3, 5, Color.BLACK))
        assertThat(result).isInstanceOf(RunningBoardState::class.java)
    }
}
