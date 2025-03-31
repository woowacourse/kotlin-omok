package woowacourse.omok.domain

sealed class PutStoneResult {
    data object Success : PutStoneResult()

    data object Finished : PutStoneResult()

    data object InvalidPosition : PutStoneResult()

    data object AlreadyPlaced : PutStoneResult()

    data object Violation : PutStoneResult()
}
