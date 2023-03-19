package controller

import domain.Point
import dto.PointDTO

object PointMapper : Mapper<Point,PointDTO> {
    override fun domainToDTO(source: Point): PointDTO {
        return PointDTO(source.x, source.y)
    }

    override fun dtoToDomain(source: PointDTO): Point {
        return Point(source.x, source.y)
    }
}
