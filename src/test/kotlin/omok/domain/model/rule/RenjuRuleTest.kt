package omok.domain.model.rule

import io.kotest.matchers.shouldBe
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import omok.doubleFourFixture
import omok.doubleThreeFixture
import omok.stoneLongMoveFixture
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule

class RenjuRuleTest {
    @Test
    fun `흑돌 차례일 때 장목이면 둘 수 없다`() {
        // Given
        val board = Board(stoneLongMoveFixture)
        val renjuRule = RenjuRule(BlackRenjuRule())

        // When
        val result = renjuRule.canPlace(OmokStone(Position(Column.from('E'), Row(5)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌 차례일 때 3-3이면 둘 수 없다`() {
        // Given
        val board = Board(doubleThreeFixture)
        val renjuRule = RenjuRule(BlackRenjuRule())

        // When
        val result = renjuRule.canPlace(OmokStone(Position(Column.from('E'), Row(3)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }

    @Test
    fun `흑돌 차례일 때 4-4면 둘 수 없다`() {
        // Given
        val board = Board(doubleFourFixture)
        val renjuRule = RenjuRule(BlackRenjuRule())

        // When
        val result = renjuRule.canPlace(OmokStone(Position(Column.from('C'), Row(13)), StoneType.BLACK), board)

        // Then
        result shouldBe false
    }
}
