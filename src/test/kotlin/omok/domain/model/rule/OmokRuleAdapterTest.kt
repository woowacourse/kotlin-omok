package omok.domain.model.rule

import io.kotest.matchers.shouldBe
import omok.diagonalDownWinStones
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import omok.doubleFourFixture
import omok.doubleThreeFixture
import omok.horizontalWinStones
import omok.stoneLongMoveFixture
import omok.verticalWinStones
import org.junit.jupiter.api.Test

class OmokRuleAdapterTest {
    private val omokRuleAdapter = OmokRuleAdapter()

    @Test
    fun `흑돌은 장목이 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(stones = stoneLongMoveFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position(Column.from('E'), Row(5)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌은 3-3이 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(stones = doubleThreeFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position(Column.from('E'), Row(3)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌은 4-4가 되는 곳에 둘 수 없다`() {
        // Given
        val board = Board(stones = doubleFourFixture)

        // When
        val result = omokRuleAdapter.checkAnyFoulCondition(OmokStone(Position(Column.from('C'), Row(13)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `세로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(stones = horizontalWinStones)
        val omokStone = OmokStone(Position(Column.from('E'), Row(1)), StoneType.BLACK)

        // When
        val newBoard = board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, newBoard) shouldBe true
    }

    @Test
    fun `대각선 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(stones = verticalWinStones)
        val omokStone = OmokStone(Position(Column.from('A'), Row(5)), StoneType.BLACK)

        // When
        val newBoard = board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, newBoard) shouldBe true
    }

    @Test
    fun `가로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(stones = diagonalDownWinStones)
        val omokStone = OmokStone(Position(Column.from('E'), Row(5)), StoneType.BLACK)

        // When
        val newBoard = board.placeStone(omokStone)

        // Then
        omokRuleAdapter.checkWin(omokStone, newBoard) shouldBe true
    }
}
