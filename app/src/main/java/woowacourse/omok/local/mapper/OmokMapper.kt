package woowacourse.omok.local.mapper

import woowacourse.omok.local.db.OmokEntity
import woowacourse.omok.presentation.model.Omok

fun OmokEntity.toPresentation(): Omok {
    return Omok(
        rowComma = rowComma,
        columnComma = columnComma,
    )
}

fun Omok.toLocal(): OmokEntity {
    return OmokEntity(
        rowComma = rowComma,
        columnComma = columnComma,
    )
}
