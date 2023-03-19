// import domain.stone.Position
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.assertAll
// import org.junit.jupiter.api.assertThrows
//
// class PositionTest {
//     @Test
//     fun `y좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다`() {
//         assertAll(
//             "y좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다",
//             { assertThrows<IllegalArgumentException> { Position(1, 15) } },
//             { assertThrows<IllegalArgumentException> { Position(1, -1) } }
//         )
//     }
//
//     @Test
//     fun `x좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다`() {
//         assertAll(
//             "x좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다",
//             { assertThrows<IllegalArgumentException> { Position(-1, 10) } },
//             { assertThrows<IllegalArgumentException> { Position(15, 10) } }
//         )
//     }
// }
