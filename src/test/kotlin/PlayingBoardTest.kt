import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
}
