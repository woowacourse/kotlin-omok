import woowacourse.omok.model.AddStoneStatus
import woowacourse.omok.model.GameBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class GameBoardTest {
    @Test
    fun `오목판에 새로운 돌을 추가할 수 있다`() {
        // given
        val gameBoard = GameBoard()
        val stone = STONE_1A_WHITE
        // when
        val existedPositionStone = STONE_1A_BLACK
        // result
        assertAll(
            { assertThat(gameBoard.addStone(stone)).isEqualTo(AddStoneStatus.IsAble) },
            { assertThat(gameBoard.addStone(existedPositionStone)).isEqualTo(AddStoneStatus.Failed.IsExist) },
        )
    }

    @Test
    fun `오목판의 마지막 돌을 불러올 수 있다`() {
        // given
        val gameBoard = GameBoard()
        // when
        gameBoard.addStone(STONE_1A_WHITE)
        gameBoard.addStone(STONE_2A_WHITE)
        gameBoard.addStone(STONE_3A_WHITE)
        // result
        assertThat(gameBoard.lastStone()).isEqualTo(STONE_3A_WHITE)
    }
}
