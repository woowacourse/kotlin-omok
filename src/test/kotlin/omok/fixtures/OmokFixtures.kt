package omok.fixtures

import omok.StoneColor
import omok.model.OmokStone

fun createOmokStone(x: Int, y: Int, color: StoneColor): OmokStone = OmokStone(createPoint(x, y), color)
