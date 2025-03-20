package omok.domain.rule

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DrawRuleTest {
    private lateinit var playingBoard: PlayingBoard

    @BeforeEach
    fun setup() {
        playingBoard = PlayingBoard()
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        for (row in 1..15) {
            for (col in 1..15) {
                val stoneColor = if ((row + col) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                val playerStone = PlayerStone(stoneColor, Position(RowPosition(row), ColumnPosition(col)))
                playingBoard.placeStone(playerStone)
            }
        }

        val playerStone = PlayerStone(StoneColor.BLACK, Position(RowPosition(15), ColumnPosition(15)))
        val actual = DrawRule().canPlace(playingBoard.board, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.DRAW)

        assertThat(actual).isEqualTo(expected)
    }
}
