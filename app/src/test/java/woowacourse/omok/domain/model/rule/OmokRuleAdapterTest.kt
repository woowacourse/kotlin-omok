package omok.domain.model.rule

import io.kotest.matchers.shouldBe
import omok.diagonalDownWinStones
import omok.doubleFourFixture
import omok.doubleThreeFixture
import omok.horizontalWinStones
import omok.stoneLongMoveFixture
import omok.verticalWinStones
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.rule.OmokRuleAdapter
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

class OmokRuleAdapterTest {
    private val omokRuleAdapter = OmokRuleAdapter()

    @Test
    fun `흑돌은 장목이 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(_stones = stoneLongMoveFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position.of(5, 5, board.size), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌은 3-3이 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(_stones = doubleThreeFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position.of(5, 3, board.size), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌은 4-4가 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(_stones = doubleFourFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position.of(3, 13, board.size), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `세로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(_stones = horizontalWinStones)
        val omokStone = OmokStone(Position.of(5, 1, board.size), StoneType.BLACK)

        // When
        board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, board) shouldBe true
    }

    @Test
    fun `대각선 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(_stones = verticalWinStones)
        val omokStone = OmokStone(Position.of(1, 5, board.size), StoneType.BLACK)

        // When
        board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, board) shouldBe true
    }

    @Test
    fun `가로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(_stones = diagonalDownWinStones)
        val omokStone = OmokStone(Position.of(5, 5, board.size), StoneType.BLACK)

        // When
        board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, board) shouldBe true
    }
}
