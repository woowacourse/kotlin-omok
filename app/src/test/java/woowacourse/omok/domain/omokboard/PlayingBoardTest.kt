package woowacourse.omok.domain.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.OmokRule

class PlayingBoardTest {
    @Test
    fun `오목판 내에서 원하는 (1,1) 위치에 검은돌을 놓는다`() {
        // given
        val playingBoard = PlayingBoard(ruleNavigation = RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules))

        // when
        playingBoard.placeStone(PlayerStone(StoneColor.BLACK, Position(1 to 1)))

        val actual = playingBoard.board.value.values.first()
        val expected = OmokBoardGridCell.Occupied(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
