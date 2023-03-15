import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PlayingBoardTest {

    @Test
    fun `특정 위치에 돌이 있으면 false를 반환한다`() {
        val playingBoard = PlayingBoard(listOf(Stone(1, 2)))
        val actual = playingBoard.isPossiblePut(Point(1, 2))

        assertThat(actual).isFalse
    }

    @Test
    fun `특정 위치에 돌이 없으면 true를 반환한다`() {
        val playingBoard = PlayingBoard(listOf(Stone(1, 2)))
        val actual = playingBoard.isPossiblePut(Point(5, 3))

        assertThat(actual).isTrue
    }

    @Test
    fun `주어진 색깔의 마지막으로 놓여진 돌의 위치를 알 수 있다`() {
        val playingBoard = PlayingBoard(
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK),
            Stone(1, 5, Color.BLACK),
        )

        assertAll(
            // TODO: 그냥 마지막 돌의 위치만 알 수 있도록
            "주어진 색깔의 마지막으로 놓여진 돌의 위치를 알 수 있다",
            {
                val actual = playingBoard.getLatestPoint(Color.WHITE)
                val expected = Point(1, 3)
                assertThat(actual).isEqualTo(expected)
            },
            {
                val actual = playingBoard.getLatestPoint(Color.BLACK)
                val expected = Point(1, 5)
                assertThat(actual).isEqualTo(expected)
            }
        )
    }
}
