package woowacourse.omok.view

import omock.model.Failure

interface PlaceErrorHandler {
    fun onError(placeFail: Failure): String
}
