package woowacourse.omok.data.local.mapper

import woowacourse.omok.data.local.entity.OmokEntity
import woowacourse.omok.model.Coordinate

class OmokEntityMapper {
    companion object {
        fun map(coordinate: Coordinate): OmokEntity {
            return OmokEntity(
                x = coordinate.x,
                y = coordinate.y,
            )
        }
    }
}
