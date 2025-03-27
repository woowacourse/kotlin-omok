package woowacourse.ui

import woowacourse.omok.domain.model.position.Position

interface OmokGameListener {
    fun onPosition(): Position

    fun onError()
}
