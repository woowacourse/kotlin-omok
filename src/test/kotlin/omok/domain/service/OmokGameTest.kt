package omok.domain.service

import omok.POSITION_ONE_FIVE
import omok.POSITION_ONE_FOUR
import omok.POSITION_ONE_ONE
import omok.POSITION_ONE_THREE
import omok.POSITION_ONE_TWO
import omok.POSITION_SEVEN_FOUR
import omok.POSITION_SEVEN_ONE
import omok.POSITION_SEVEN_THREE
import omok.POSITION_SEVEN_TWO
import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PlayingBoard
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `플레이어는 한 번씩 번갈아가며 돌을 둘 수 있다`() {
        // given
        val blackTurns =
            mutableListOf(
                POSITION_ONE_ONE,
                POSITION_ONE_TWO,
                POSITION_ONE_THREE,
                POSITION_ONE_FOUR,
                POSITION_ONE_FIVE,
            )
        val whiteTurns =
            mutableListOf(
                POSITION_SEVEN_ONE,
                POSITION_SEVEN_TWO,
                POSITION_SEVEN_THREE,
                POSITION_SEVEN_FOUR,
            )

        // when & then
        val omokGame = OmokGame(PlayingBoard(OmokBoard.create()))
        var expected = StoneColor.WHITE

        omokGame.start({ stoneColor, _ ->
            expected = expected.reversed()
            assertThat(stoneColor).isEqualTo(expected)
            when (stoneColor) {
                StoneColor.BLACK -> blackTurns.removeFirst()
                StoneColor.WHITE -> whiteTurns.removeFirst()
            }
        }, {
        })
    }
}
