package woowacourse.omok.presentation

import woowacourse.omok.local.repository.OmokRepository
import woowacourse.omok.presentation.Exception.DbException.DeleteAllOmokException
import woowacourse.omok.presentation.Exception.DbException.DeleteOmokException
import woowacourse.omok.presentation.Exception.DbException.InsertOmokException
import woowacourse.omok.presentation.Exception.DbException.SelectOmokException
import woowacourse.omok.presentation.model.Omok

class MainViewModel(private val repository: OmokRepository) {
    fun insertOmok(omok: Omok): UiState {
        repository.insertOmok(omok).onSuccess {
            return UiState.Success.Empty
        }
        return UiState.Failure(InsertOmokException())
    }

    fun selectOmok(): UiState {
        repository.selectOmok().onSuccess { omoks ->
            return UiState.Success.Loaded(omoks)
        }
        return UiState.Failure(SelectOmokException())
    }

    fun deleteOmok(omok: Omok): UiState {
        repository.deleteOmok(omok = omok).onSuccess {
            return UiState.Success.Empty
        }
        return UiState.Failure(DeleteOmokException())
    }

    fun deleteAllOmok(): UiState {
        repository.deleteAllOmok().onSuccess {
            return UiState.Success.Empty
        }
        return UiState.Failure(DeleteAllOmokException())
    }
}
