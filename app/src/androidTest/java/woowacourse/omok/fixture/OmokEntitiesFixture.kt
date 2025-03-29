package woowacourse.omok.fixture

import woowacourse.omok.data.db.omok.OmokEntity

val omokEntities =
    arrayOf(
        OmokEntity(roomId = 1, 1, 1, "BLACK"),
        OmokEntity(roomId = 1, 2, 2, "WHITE"),
        OmokEntity(roomId = 1, 3, 3, "BLACK"),
    )

val duplicateOmokEntities =
    arrayOf(
        OmokEntity(roomId = 1, 1, 1, "BLACK"),
        OmokEntity(roomId = 2, 1, 1, "WHITE"),
    )
