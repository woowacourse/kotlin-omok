package omok

import omok.domain.point.Column
import omok.domain.point.OmokPoint
import omok.domain.point.Row

val POINT_H5 = OmokPoint(Row(4), Column(7))
val POINT_H6 = OmokPoint(Row(5), Column(7))
val POINT_H7 = OmokPoint(Row(6), Column(7))
val POINT_H8 = OmokPoint(Row(7), Column(7))
val POINT_H9 = OmokPoint(Row(8), Column(7))
val POINT_H10 = OmokPoint(Row(9), Column(7))
val POINT_E8 = OmokPoint(Row(7), Column(4))
val POINT_F8 = OmokPoint(Row(7), Column(5))
val POINT_G8 = OmokPoint(Row(7), Column(6))

fun beforeDoubleThree(): List<OmokPoint> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8)

fun beforeDoubleFour(): List<OmokPoint> = listOf(POINT_H7, POINT_G8, POINT_H6, POINT_F8, POINT_E8, POINT_H5)

fun beforeOverLine(): List<OmokPoint> = listOf(POINT_H5, POINT_H6, POINT_H7, POINT_H9, POINT_H10)

fun getFoulPoint(): OmokPoint = POINT_H8

fun omokPoints(): List<OmokPoint> = listOf(POINT_H5, POINT_H6, POINT_H7, POINT_H8, POINT_H9)
