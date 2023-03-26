import domain.judgement.RenjuRule
import domain.stone.Color
import domain.turn.Board
import domain.turn.FinishedBoardState
import domain.turn.RunningBoardState
import org.assertj.core.api.Assertions.assertThat
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
                Board(nextStones.convertToBoard()),
                latestStone
            )
        }
    }

    @Test
    fun `돌을 추가해도 아직 게임이 진행중이라면  RunningBoardState를 반환한다`() {
        val latestStone = Stone(2, 4, Color.WHITE)
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
        val runningBoardState =
            RunningBoardState(RenjuRule(), Board(stones.convertToBoard()), latestStone)
        val newStone = Stone(1, 6, Color.BLACK)

        val result = runningBoardState.putStone(newStone)
        assertThat(result).isInstanceOf(RunningBoardState::class.java)
    }

    @Test
    fun `돌을 추가해서 게임이 종료된다면  FinishedBoardState를 반환한다`() {
        val latestStone = Stone(2, 4, Color.WHITE)
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
        val runningBoardState =
            RunningBoardState(RenjuRule(), Board(stones.convertToBoard()), latestStone)
        val newStone = Stone(1, 5, Color.BLACK)

        val result = runningBoardState.putStone(newStone)
        assertThat(result).isInstanceOf(FinishedBoardState::class.java)
    }

    @Test
    fun `규칙에 어긋난다면 자기 자신을 반환한다`() {
        val latestStone = Stone(2, 4, Color.WHITE)
        val stones = listOf(
            Stone(7, 7, Color.BLACK),
            Stone(2, 1, Color.WHITE),
            Stone(7, 9, Color.BLACK),
            Stone(2, 2, Color.WHITE),
            Stone(6, 8, Color.BLACK),
            Stone(2, 3, Color.WHITE),
            Stone(8, 8, Color.BLACK),
            Stone(2, 4, Color.WHITE),
        )
        val newStone = Stone(7, 8, Color.BLACK)

        val runningBoardState =
            RunningBoardState(RenjuRule(), Board(stones.convertToBoard()), latestStone)
        val result = runningBoardState.putStone(newStone)
        assertThat(result).isSameAs(runningBoardState)
    }
}
