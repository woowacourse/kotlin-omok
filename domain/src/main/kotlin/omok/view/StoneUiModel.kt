package omok.view

import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

fun StoneState.toUiModel(): StoneUiModel = when (this) {
    BlackStoneState -> StoneUiModel.BLACK
    WhiteStoneState -> StoneUiModel.WHITE
    else -> StoneUiModel.EMPTY
}

enum class StoneUiModel(val text: String) {
    BLACK("흑"),
    WHITE("백"),
    EMPTY(""),
}
