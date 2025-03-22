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
        playingBoard = PlayingBoard(rules = OmokRule.rules)
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        for (row in 1..15) {
            for (column in 1..15) {
                if (row == 15 && column == 15) break

                val stoneColor = if ((row + column) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                playingBoard.board.find(Position(RowPosition(row), ColumnPosition(column)))?.updateState(stoneColor)
            }
        }

        // when
        val playerStone = PlayerStone(StoneColor.BLACK, Position(RowPosition(15), ColumnPosition(15)))
        val actual = DrawRule().canPlace(playingBoard.board, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.DRAW)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
