package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard

sealed interface State {
    val omokBoard: OmokBoard
}
