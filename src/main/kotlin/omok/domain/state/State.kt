package omok.domain.state

import omok.domain.OmokBoard

sealed interface State {
    val omokBoard: OmokBoard
}
