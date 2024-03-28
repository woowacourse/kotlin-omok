package omok.model

import omok.model.rule.ContinualStonesStandard
import omok.model.rule.ForbiddenRules
import omok.model.rule.GamePlayingRules
import omok.model.rule.RuleAdapter2
import omok.model.rule.ban.ForbiddenPlaces
import omok.model.rule.winning.ContinualStonesWinningCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var emptyBoard: Board
    private lateinit var player: Player
    private lateinit var player2: Player2
    private val playerStone = Stone.BLACK

    @BeforeEach
    fun setUp() {
        emptyBoard = Board()
        player =
            Player(
                playerStone,
                GamePlayingRules.from(
                    ContinualStonesWinningCondition(ContinualStonesStandard(5), ContinualStonesCondition.EXACT),
                    ForbiddenPlaces(),
                ),
            )
        player2 =
            Player2(
                playerStone,
                RuleAdapter2(
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
    fun `오목판에 돌을 놓는다2`() {
        emptyBoard.place(Position(3, 3), player2)

        val actual = emptyBoard.find(Position(3, 3))
        assertThat(actual).isEqualTo(playerStone)
    }

    @Test
    fun `오목판에 이미 돌이 있는 곳에 놓으면 예외가 발생한다`() {
        val board =
            initBoard(
                StonePosition(Position(3, 3), playerStone),
            )
        assertThrows<IllegalStateException> { board.place(Position(3, 3), player) }
    }
}
