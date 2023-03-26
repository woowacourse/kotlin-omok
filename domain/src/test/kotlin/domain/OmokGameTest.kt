package domain

import org.assertj.core.api.Assertions.assertThat
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
        val stone = omokGame.getStone(Position(1, 6))
        omokGame.placeTo(stone)
        val actual = omokGame.getWinnerColor()
        val expected = Color.BLACK
        // then
        assertThat(actual).isEqualTo(expected)
    }

    private fun generateBlackWinOmokBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 1, 2))
            placeStone(Stone(Color.WHITE, 2, 2))
            placeStone(Stone(Color.BLACK, 1, 3))
            placeStone(Stone(Color.WHITE, 2, 3))
            placeStone(Stone(Color.BLACK, 1, 4))
            placeStone(Stone(Color.WHITE, 2, 4))
            placeStone(Stone(Color.BLACK, 1, 5))
            placeStone(Stone(Color.WHITE, 4, 8))
        }
        return board
    }

    @Test
    fun `백이 이겼을 경우 하얀색을 반환한다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        val omokGame = OmokGame(board)
        // when
        val stone = omokGame.getStone(Position(1, 6))
        omokGame.placeTo(stone)
        val actual = omokGame.getWinnerColor()
        val expected = Color.WHITE
        // then
        assertThat(actual).isEqualTo(expected)
    }

    private fun generateWhiteWinOmokBoard(): Board {
        val board = Board(rule = RenjuRuleAdapter()).apply {
            placeStone(Stone(Color.BLACK, 2, 2))
            placeStone(Stone(Color.WHITE, 1, 2))
            placeStone(Stone(Color.BLACK, 2, 3))
            placeStone(Stone(Color.WHITE, 1, 3))
            placeStone(Stone(Color.BLACK, 2, 4))
            placeStone(Stone(Color.WHITE, 1, 4))
            placeStone(Stone(Color.BLACK, 2, 5))
            placeStone(Stone(Color.WHITE, 1, 5))
            placeStone(Stone(Color.BLACK, 2, 10))
        }
        return board
    }

    @Test
    fun `게임을 초기화 하면 돌의 개수는 0 이된다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        val omokGame = OmokGame(board)

        // when
        omokGame.resetGame()
        val actual = omokGame.board.stones.values.size
        val expected = 0

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `게임을 초기화 하면 게임의 턴은 흑이된다`() {
        // given
        val board = generateWhiteWinOmokBoard()
        val omokGame = OmokGame(board)
        // when
        omokGame.resetGame()
        val actual = omokGame.currentColor
        val expected = Color.BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막에 위치한 돌이 검정색이라면, 다음 차례는 흰색이다`() {
        val board = Board(Stones(listOf(Stone(Color.BLACK, 1, 1))), RenjuRuleAdapter())
        val omokGame = OmokGame(board)
        // when
        val actual = omokGame.currentColor
        val expected = Color.WHITE

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막에 위치한 돌이 흰색이라면, 다음 차례는 검정색이다`() {
        val board = Board(Stones(listOf(Stone(Color.WHITE, 1, 1))), RenjuRuleAdapter())
        val omokGame = OmokGame(board)
        // when
        val actual = omokGame.currentColor
        val expected = Color.BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
