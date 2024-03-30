package woowacourse.omok.data.local.mapper

import woowacourse.omok.Coordinate
import woowacourse.omok.data.local.entity.OmokEntity

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
