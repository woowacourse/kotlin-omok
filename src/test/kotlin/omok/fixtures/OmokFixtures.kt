package omok.fixtures

import omok.model.OmokStone
import omok.model.StoneColor

fun createOmokStone(
    x: Int,
    y: Int,
    color: StoneColor,
): OmokStone = OmokStone(createPoint(x, y), color)

fun createBlackStone(
    x: Int,
    y: Int,
): OmokStone = OmokStone(createPoint(x, y), StoneColor.BLACK)

fun createWhiteStone(
    x: Int,
    y: Int
): OmokStone = OmokStone(createPoint(x, y), StoneColor.WHITE)
