package serdjuk.com

import com.badlogic.gdx.Gdx

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
fun String.tryInt() = try {
    this.toInt()
} catch (_: Exception) {
    null
}


fun String.tryHex(hexLength:Int) = try {
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