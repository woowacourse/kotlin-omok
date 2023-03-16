import domain.stone.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasedBoardTest {

    @Test
    fun `마지막으로 놓인 돌을 반환한다`() {
        val basedBoard = BasedBoard(
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
            Stone(1, 5, Color.BLACK),
        )

        val actual = basedBoard.getLatestStone()
        val expected = Stone(1, 5, Color.BLACK)
        assertThat(actual).isEqualTo(expected)
    }
}
