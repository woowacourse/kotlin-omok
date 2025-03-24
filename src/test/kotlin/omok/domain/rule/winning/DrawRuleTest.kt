package omok.domain.rule.winning

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.OmokBoard
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
        playingBoard = PlayingBoard(OmokBoard.create(5, 5))
    }

    @Test
    fun `모든 칸이 채워지면 무승부를 반환한다`() {
        // given
        for (row in 1..5) {
            for (column in 1..5) {
                if (row == 5 && column == 5) break

                val stoneColor = if ((row + column) % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
                playingBoard.board.find(Position(RowPosition(row), ColumnPosition(column)))?.updateState(stoneColor)
            }
        }

        // when
        val playerStone = PlayerStone(StoneColor.BLACK, Position(RowPosition(15), ColumnPosition(15)))
        val actual = DrawRule().perform(playingBoard.board, playerStone)
        val expected = JudgeResult.Finished.Draw

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
