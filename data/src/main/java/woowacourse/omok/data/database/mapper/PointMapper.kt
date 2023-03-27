package woowacourse.omok.data.database.mapper

import domain.point.PointDomain
import woowacourse.omok.data.database.entity.PointData

fun PointData.toDomain(): PointDomain =
    PointDomain(
        row = y,
        col = x,
    )

fun PointDomain.toData(): PointData =
    PointData(
        x = col,
        y = row,
    )
