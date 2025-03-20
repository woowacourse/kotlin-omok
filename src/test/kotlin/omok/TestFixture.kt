package omok

import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.StoneType

val horizontalWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to StoneType.BLACK,
        Position(Column.from('B'), Row(1)) to StoneType.BLACK,
        Position(Column.from('C'), Row(1)) to StoneType.BLACK,
        Position(Column.from('D'), Row(1)) to StoneType.BLACK,
        Position(Column.from('E'), Row(1)) to StoneType.BLACK,
    )

val verticalWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to StoneType.BLACK,
        Position(Column.from('A'), Row(2)) to StoneType.BLACK,
        Position(Column.from('A'), Row(3)) to StoneType.BLACK,
        Position(Column.from('A'), Row(4)) to StoneType.BLACK,
        Position(Column.from('A'), Row(5)) to StoneType.BLACK,
    )

val diagonalDownWinStones =
    mapOf(
        Position(Column.from('A'), Row(1)) to StoneType.BLACK,
        Position(Column.from('B'), Row(2)) to StoneType.BLACK,
        Position(Column.from('C'), Row(3)) to StoneType.BLACK,
        Position(Column.from('D'), Row(4)) to StoneType.BLACK,
        Position(Column.from('E'), Row(5)) to StoneType.BLACK,
    )
