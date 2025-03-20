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

val doubleThreeFixture =
    mapOf(
        Position(Column.from('B'), Row(6)) to StoneType.BLACK,
        Position(Column.from('E'), Row(6)) to StoneType.BLACK,
        Position(Column.from('C'), Row(5)) to StoneType.BLACK,
        Position(Column.from('E'), Row(5)) to StoneType.BLACK,
    )

val doubleFourFixture =
    mapOf(
        Position(Column.from('C'), Row(10)) to StoneType.BLACK,
        Position(Column.from('C'), Row(11)) to StoneType.BLACK,
        Position(Column.from('C'), Row(12)) to StoneType.BLACK,
        Position(Column.from('C'), Row(14)) to StoneType.BLACK,
        Position(Column.from('C'), Row(15)) to StoneType.BLACK,
        Position(Column.from('D'), Row(12)) to StoneType.BLACK,
    )

val stoneLongMoveFixture =
    mapOf(
        Position(Column.from('B'), Row(2)) to StoneType.BLACK,
        Position(Column.from('C'), Row(3)) to StoneType.BLACK,
        Position(Column.from('D'), Row(4)) to StoneType.BLACK,
        Position(Column.from('F'), Row(6)) to StoneType.BLACK,
        Position(Column.from('G'), Row(7)) to StoneType.BLACK,
    )
