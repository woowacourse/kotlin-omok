package omok.model

sealed interface BoardResult {
    data object Omok : BoardResult

    data object Done : BoardResult

    data object Duplicate : BoardResult

    data object Block : BoardResult
}
