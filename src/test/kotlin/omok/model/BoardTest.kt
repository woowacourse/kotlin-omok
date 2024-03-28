package omok.model

import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ForbiddenRules
import omok.model.rule.RuleAdapter
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var emptyBoard: Board
    private lateinit var player: Player
    private val playerStone = Stone.BLACK

    @BeforeEach
    fun setUp() {
        emptyBoard = Board()
        player =
            Player(
                playerStone,
                RuleAdapter(
                    ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                    ForbiddenRules(),
                ),
            )
    }

    @Test
    fun `오목판에 돌을 놓는다`() {
        emptyBoard.place(Position(3, 3), player)

        val actual = emptyBoard.find(Position(3, 3))
        assertThat(actual).isEqualTo(playerStone)
    }

    @Test
    fun `비어있는 위치인지 확인한다`() {
        val actual = emptyBoard.emptyPosition(Position(4, 4))

        assertThat(actual).isTrue
    }
}
