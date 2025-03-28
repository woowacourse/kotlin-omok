package woowacourse.omok.domain.game

import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.rule.OmokRules
import woowacourse.omok.ui.ext.getPointAt

class OmokGame(val omokBoard: OmokBoard, val omokRules: OmokRules) {
    fun onProtected(
        x: Int,
        y: Int,
    ) {
        omokBoard.getPointAt(x, y) is Protected && omokBoard.getPointAt(x, y) is Black
    }

    fun onFinished(stone: Place) {
        omokRules.isOmok(stone, omokBoard) || !omokBoard.isNotFull()
    }
}
