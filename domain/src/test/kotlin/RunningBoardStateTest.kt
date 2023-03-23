import domain.judgement.RenjuRule
import domain.stone.Color
import domain.turn.RunningBoardState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RunningBoardStateTest {
    @Test
    fun `이미 게임이 종료된 보드를 넣으면 RunningBoardState 객체 생성 안됨`() {
        val latestStone = Stone(1, 5, Color.BLACK)
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
            RunningBoardState(
                RenjuRule(),
                nextStones.convertToBoard(),
                latestStone
            )
        }
    }
}
