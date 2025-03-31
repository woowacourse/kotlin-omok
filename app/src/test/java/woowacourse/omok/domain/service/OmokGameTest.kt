package woowacourse.omok.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.omokboard.ColumnPosition
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.omokboard.RowPosition
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.OmokRule

class OmokGameTest {
    @Test
    fun `플레이어는 게임을 마칠 때까지 한 번씩 번갈아가며 돌을 둘 수 있다`() {
        // given
        val blackTurnPosition = Position(RowPosition(1), ColumnPosition(2))

        // when & then
        val omokGame = OmokGame(PlayingBoard(OmokBoard(), RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules)))
        val expected = PlaceResult::class.java

        omokGame.start(blackTurnPosition) {
                placeResult ->
            assertThat(placeResult).isInstanceOf(expected)
        }
    }

    @Test
    fun `플레이어의 placeResult가 GameOnGoing이면 턴을 바꾼다`() {
        // given
        val blackTurnPosition = Position(RowPosition(1), ColumnPosition(2))
        val omokGame = OmokGame(PlayingBoard(OmokBoard(), RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules)))

        // when
        val actualInitStone = omokGame.stoneColor

        omokGame.start(blackTurnPosition) {}

        val actualChangeStone = omokGame.stoneColor

        val expectedInitStone = StoneColor.BLACK
        val expectedChangeStone = StoneColor.WHITE

        // then
        assertThat(actualInitStone).isEqualTo(expectedInitStone)
        assertThat(actualChangeStone).isEqualTo(expectedChangeStone)
    }

    @Test
    fun `플레이어의 placeResult가 GameOnGoing이 아니면 턴을 바꾸지 않는다`() {
        // given
        val blackTurnPosition = Position(RowPosition(1), ColumnPosition(2))
        val omokGame = OmokGame(PlayingBoard(OmokBoard(), RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules)))

        // when
        val actualInitStone = omokGame.stoneColor

        omokGame.start(blackTurnPosition) {}

        val actualChangeStone = omokGame.stoneColor

        val expectedInitStone = StoneColor.BLACK
        val expectedChangeStone = StoneColor.WHITE

        // then
        assertThat(actualInitStone).isEqualTo(expectedInitStone)
        assertThat(actualChangeStone).isEqualTo(expectedChangeStone)
    }
}
