// class InputView {
//
//     fun requestPoint(placedStones: placedStones): Point {
//         while (true) {
//             if (placedStones.getColor() == Color.BLACK) {
//                 println("흑의 차례입니다.")
//                 print("위치를 입력하세요: ")
//                 val input = readln()
//                 val x = convertX(input[0])
//                 val y = input.substring(1).toInt()
//                 kotlin.runCatching { Point(x, y) }
//                     .onSuccess { return it }
//                     .onFailure { println(it.message) }
//             }
//             if (placedStones.getColor() == Color.WHITE) {
//                 println("백의 차례입니다.")
//                 print("위치를 입력하세요: ")
//                 val input = readln()
//                 val x = convertX(input[0])
//                 val y = input.substring(1).toInt()
//                 kotlin.runCatching { Point(x, y) }
//                     .onSuccess { return it }
//                     .onFailure { println(it.message) }
//             }
//         }
//     }
//
//     fun convertX(char: Char): Int = char.code - 64
// }
