import domain.judgement.RenjuRule
import domain.stone.Color
import domain.stone.Position
import domain.turn.StartBoardState
import domain.turn.Turn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `Turn 객체에게 위치를 주면 다음 턴 색상을 가리키는 새로운 Turn객체를 반환한다`() {
        val turn = Turn(Color.BLACK, StartBoardState(RenjuRule()))
        val newPosition = Position(3, 4)
        val result = turn.putStone(newPosition)
        assertThat(result.color).isEqualTo(Color.WHITE)
    }
}
