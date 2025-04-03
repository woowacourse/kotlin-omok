package woowacourse.omok.ui.mapper

import woowacourse.omok.domain.model.Game
import woowacourse.omok.ui.model.GameUiModel

fun Game.toGameUiModel() = GameUiModel(id, name)
