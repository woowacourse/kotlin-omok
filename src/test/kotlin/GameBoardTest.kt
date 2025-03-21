import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class GameBoardTest {
    @Test
    fun `오목판에 새로운 돌을 추가할 수 있다`() {
        val gameBoard = GameBoard()
        val stone = Stone(Position(Row.from(1), Col.from('A')), StoneColor.WHITE)
        val existedPositionStone = Stone(Position(Row.from(1), Col.from('A')), StoneColor.BLACK)

        assertAll(
            { assertThat(gameBoard.addStone(stone)).isEqualTo(AddStoneStatus.IsAble) },
            { assertThat(gameBoard.addStone(existedPositionStone)).isEqualTo(AddStoneStatus.IsExist) },
        )
    }
}
