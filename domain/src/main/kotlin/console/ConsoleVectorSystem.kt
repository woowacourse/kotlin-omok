package console

import dto.VectorDTO
import dto.VectorSystem

class ConsoleVectorSystem(val vector: VectorDTO) : VectorSystem {
    override fun toDTO(): VectorDTO {
        return vector
    }
}
