package domain

import domain.domain.Board
import domain.domain.Color
import domain.domain.Position
import domain.domain.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class OmokGameTest {
    private fun Stone(color: Color, vararg positions: Int): Stone {
        return Stone(color, Position(positions[0], positions[1]))
    }

    @Test
    fun `흑이 이겼을 경우 검정색을 반환한다`() {
        // given
        val board = generateBlackWinOmokBoard()
        val omokGame = OmokGame(board)
        // when
        val actual = omokGame.getWinnerColor({}, { Position(1, 6) })
        val expected = Color.BLACK
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private fun generateBlackWinOmokBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 1, 2))
            placeStone(Stone(getCurrentTurn(), 2, 2))
            placeStone(Stone(getCurrentTurn(), 1, 3))
            placeStone(Stone(getCurrentTurn(), 2, 3))
            placeStone(Stone(getCurrentTurn(), 1, 4))
            placeStone(Stone(getCurrentTurn(), 2, 4))
            placeStone(Stone(getCurrentTurn(), 1, 5))
            placeStone(Stone(getCurrentTurn(), 4, 8))
        }
        return board
    }

    @Test
    fun `백이 이겼을 경우 하얀색을 반환한다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        val omokGame = OmokGame(board)
        // when
        val actual = omokGame.getWinnerColor({}, { Position(1, 6) })
        val expected = Color.WHITE
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private fun generateWhiteWinOmokBoard(): Board {
        val board = Board().apply {
            placeStone(Stone(getCurrentTurn(), 2, 2))
            placeStone(Stone(getCurrentTurn(), 1, 2))
            placeStone(Stone(getCurrentTurn(), 2, 3))
            placeStone(Stone(getCurrentTurn(), 1, 3))
            placeStone(Stone(getCurrentTurn(), 2, 4))
            placeStone(Stone(getCurrentTurn(), 1, 4))
            placeStone(Stone(getCurrentTurn(), 2, 5))
            placeStone(Stone(getCurrentTurn(), 1, 5))
            placeStone(Stone(getCurrentTurn(), 2, 10))
        }
        return board
    }

    @Test
    fun `처음 수와 두번째 수에 아무도 이기지못하고 3번째수에 백이 이겼을 때 하얀색을 반환한다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        val omokGame = OmokGame(board)
        // when
        val actual = omokGame.getWinnerColor({}, { getPosition() })
        val expected = Color.WHITE
        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private var count = 0
    private fun getPosition(): Position {
        count++
        if (count == 1) return Position(5, 10)
        if (count == 2) return Position(5, 11)
        return Position(1, 6)
    }
}
