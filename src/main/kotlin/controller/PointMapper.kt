package controller

import domain.Point
import dto.PointDTO

object PointMapper : Mapper<Point, PointDTO> {
    override fun domainToDTO(domain: Point): PointDTO {
        return PointDTO(domain.x, domain.y)
    }

    override fun dtoToDomain(dto: PointDTO): Point {
        return Point(dto.x, dto.y)
    }
}
