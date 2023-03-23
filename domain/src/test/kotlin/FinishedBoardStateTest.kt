import domain.judgement.RenjuRule
import domain.stone.Color
import domain.turn.FinishedBoardState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FinishedBoardStateTest {
    @Test
    fun `아직 게임이 종료되지 않은 보드를 넣으면 FinishedBoardState 객체 생성 안됨`() {
        val latestStone = Stone(1, 7, Color.BLACK)
        val stones = listOf(
            Stone(1, 1, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(1, 2, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(1, 3, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
            Stone(2, 4, Color.WHITE),
        )
        val nextStones = stones + latestStone
        assertThrows<IllegalStateException> {
            FinishedBoardState(
                RenjuRule(),
                nextStones.convertToBoard(),
                latestStone
            )
        }
    }
}
