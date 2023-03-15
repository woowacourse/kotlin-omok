import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasedBoardTest {

    @Test
    fun `주어진 색깔의 마지막으로 놓여진 돌의 위치를 알 수 있다`() {
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
