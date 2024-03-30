package woowacourse.omok.presentation

import woowacourse.omok.presentation.model.Omok

sealed interface UiState {
    sealed class Success : UiState {
        data class Loaded(val data: List<Omok>) : Success()

        data object Empty : Success()
    }

    data class Failure(val error: Throwable) : UiState
}

sealed interface Exception {
    sealed class DbException : Throwable() {
        class InsertOmokException : Throwable()

        class SelectOmokException : Throwable()

        class DeleteOmokException : Throwable()

        class DeleteAllOmokException : Throwable()
    }
}
