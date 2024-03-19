package omock

//import omock.model.Column
//import omock.model.Row
//import org.junit.jupiter.api.Test
//import java.lang.IllegalArgumentException
//
////상태 (좌표(돌)) -> state(stone) 이상적
////플레이어가 돌을 뒤집는다  -> 보드 (15 * 15(상태(돌(좌표)))) -> 3선택 오셜록
////State
////Init - NONE
////Done - White,black
//
//
//class WhitePlayer() : Player()
//
//class BlackPlayer() : Player()
//
//sealed class Type {
//    class NONE(): Type(),
//    class BLACK(): Type(),
//    class WHITE(): Type()
//}
//fun makePlayer(vararg type: Type){
//
//}
//
//interface StoneState {
//    fun put(player: Player) : StoneState
//}
//
//abstract class None() : StoneState {
//    override fun put(player: Player): StoneState {
//      return  when (player) {
//           is BlackPlayer -> Black()
//          is Type.WHITE -> White()
//            Type.NONE -> throw IllegalArgumentException()
//        }
//    }
//
//}
//
//abstract class Done() : StoneState {
//    override fun put(type : Type): StoneState {
//        throw IllegalArgumentException()
//    }
//
//}
//
//class White() : Done() {
//
//}
//
//
//class Black() : Done() {
//
//}
//
//class Init() : None() {
//
//}

//class StoneStateTest {
//    @Test
//    fun ` `(){
//        makePlayer(Type.NONE(),Type.BLACK(),Type.WHITE())
//    }
//}
