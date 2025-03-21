package omok.domain.stone

import omok.domain.ext.toCoordination
import omok.domain.point.Point

@JvmInline
value class LatestStone(val value: String) {
    fun saveLatestStone(point: Point): LatestStone {
        return LatestStone(point.toCoordination())
    }
}
