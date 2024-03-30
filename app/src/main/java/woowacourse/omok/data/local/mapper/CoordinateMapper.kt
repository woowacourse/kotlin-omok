package woowacourse.omok.data.local.mapper

import woowacourse.omok.data.local.entity.OmokEntity
import woowacourse.omok.model.Coordinate

class CoordinateMapper {
    companion object {
        fun map(omokEntity: OmokEntity): Coordinate {
            return Coordinate(
                x = omokEntity.x,
                y = omokEntity.y,
            )
        }
    }
}
