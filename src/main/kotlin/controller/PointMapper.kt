package controller

import domain.Point
import dto.PointDTO

object PointMapper {
    fun domainToDTO(point: Point): PointDTO {
        return PointDTO(point.x, point.y)
    }

    fun dtoToDomain(point: PointDTO): Point {
        return Point(point.x, point.y)
    }
}
