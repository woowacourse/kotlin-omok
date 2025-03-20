package omok.domain.model.state

import io.kotest.matchers.types.shouldBeTypeOf
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.StoneType
import org.junit.jupiter.api.Test

class BlackStoneTurnTest {
    @Test
    fun `흑돌을 놓았을 때 오목이 되지 않으면 백돌의 차례가 된다`() {
        // Given
        val board = Board()
        val state = BlackStoneTurn(board)

        // When
        val newState = state.placeStone { Position(Column.from('A'), Row(1)) }

        // Then
        newState.shouldBeTypeOf<WhiteStoneTurnTest>()
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임이 종료된다`() {
        // Given
        val board =
            Board(
                mapOf(
                    Position(Column.from('A'), Row(1)) to StoneType.BLACK,
                    Position(Column.from('B'), Row(1)) to StoneType.BLACK,
                    Position(Column.from('C'), Row(1)) to StoneType.BLACK,
                    Position(Column.from('D'), Row(1)) to StoneType.BLACK,
                ),
            )
        val state = BlackStoneTurn(board)

        // When
        val newState = state.placeStone { Position(Column.from('E'), Row(1)) }

        // Then
        newState.shouldBeTypeOf<Finish>()
    }
}
