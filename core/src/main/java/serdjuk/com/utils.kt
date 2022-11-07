package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import java.util.TreeSet

const val HASH = "#"
val regAF = Regex("[a-fA-F]")
val regHex = Regex("^[#0-9a-fA-F]")


fun getHexFocusAddress(): Int {
    val focusedHorizontal = ((hexFocusedByteRectangle.x - HEX_ADDRESS_WIDTH) / hexFocusedByteRectangle.width).toInt()
    val focusedVertical =
        ((Gdx.graphics.height - hexFocusedByteRectangle.y - TOP_HEIGHT) / hexFocusedByteRectangle.height).toInt()
    hexFocusedAddress = (focusedVertical * hexWidth + focusedHorizontal + startAddress).toInt()
    return hexFocusedAddress
}


//------------------------------------------------------------------------------------

private val firstLagDelta = 0.2f
private val nextLagDelta = firstLagDelta / 8f
private var lagDelta = -1f
private val lagKeys = TreeSet<Int>()
fun Input.isLagPressed(key: Int): Boolean {


    if (Gdx.input.isKeyPressed(key)) {
        if (!lagKeys.contains(key)) {
            lagDelta = firstLagDelta
            lagKeys.add(key)
            return true
        } else {
            lagDelta -= Gdx.graphics.deltaTime
            if (lagDelta < 0f) {
                lagDelta = nextLagDelta
                return true
            }
        }
    } else {
        lagKeys.remove(key)
        return false
    }
    return false
}


//------------------------------------------------------------------------------------

fun String.tryInt() = try {
    this.toInt()
} catch (_: Exception) {
    null
}


fun String.tryHex(hexLength: Int) = try {
    if (this.contains(HASH) && this.first() == HASH.first()) {
        this.drop(1).toInt(16)
    } else if (this.contains(regAF) && this.length < hexLength) {
        this.toInt(16)
    } else {
        this.tryInt()
    }
} catch (_: Exception) {
    null
}