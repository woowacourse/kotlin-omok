import domain.board.FinishedBoard
import domain.board.PlayingBoard
import domain.stone.Color
import domain.stone.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PlayingBoardTest {

    @Test
    fun `특정 위치에 돌이 있으면 false를 반환한다`() {
        val playingBoard = PlayingBoard(listOf(Stone(1, 2)))
        val actual = playingBoard.isPossiblePut(Position(1 - 1, 2 - 1))
        assertThat(actual).isFalse
    }

    @Test
    fun `특정 위치에 돌이 없으면 true를 반환한다`() {
        val playingBoard = PlayingBoard(listOf(Stone(1, 2)))
        val actual = playingBoard.isPossiblePut(Position(5 - 1, 3 - 1))

        assertThat(actual).isTrue
    }

    @Test
    fun `돌이 5개 이상 이어지면 FinishedBoard를 반환한다`() {
        val playingBoard = PlayingBoard(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.WHITE)
        )
        val actual = playingBoard.putStone(Stone(1, 5, Color.WHITE))
        assertThat(actual).isInstanceOf(FinishedBoard::class.java)
    }

    @Test
    fun `돌이 5개 이상 이어지 않았으면 PlayingBoard를 반환한다`() {
        val playingBoard = PlayingBoard(
            Stone(1, 1, Color.WHITE),
            Stone(1, 2, Color.WHITE),
            Stone(1, 3, Color.WHITE),
            Stone(1, 4, Color.BLACK)
        )
        val actual = playingBoard.putStone(Stone(1, 5, Color.WHITE))
        assertThat(actual).isInstanceOf(PlayingBoard::class.java)
    }

    @Test
    fun `winningColor를 참조하면 예외를 던진다`() {
        val playingBoard = PlayingBoard()

        assertThrows<IllegalStateException> { playingBoard.winningColor }
    }
}
