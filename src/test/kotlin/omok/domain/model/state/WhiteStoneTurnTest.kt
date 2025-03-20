package omok.domain.model.state

import io.kotest.matchers.types.shouldBeTypeOf
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.StoneType
import org.junit.jupiter.api.Test

class WhiteStoneTurnTest {
    @Test
    fun `백돌을 놓았을 때 오목이 되지 않으면 흑돌의 차례가 된다`() {
        // Given
        val board = Board()
        val state = WhiteStoneTurn(board)

        // When
        val newState = state.placeStone { Position(Column.from('A'), Row(1)) }

        // Then
        newState.shouldBeTypeOf<BlackStoneTurn>()
    }

    @Test
    fun `백돌을 놓았을 때 오목이 되면 게임이 종료된다`() {
        // Given
        val board =
            Board(
                mapOf(
                    Position(Column.from('A'), Row(1)) to StoneType.WHITE,
                    Position(Column.from('B'), Row(1)) to StoneType.WHITE,
                    Position(Column.from('C'), Row(1)) to StoneType.WHITE,
                    Position(Column.from('D'), Row(1)) to StoneType.WHITE,
                ),
            )
        val state = WhiteStoneTurn(board)

        // When
        val newState = state.placeStone { Position(Column.from('E'), Row(1)) }

        // Then
        newState.shouldBeTypeOf<Finish>()
    }
}
