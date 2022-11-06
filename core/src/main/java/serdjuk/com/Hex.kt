package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Hex {

    val texture = Texture("hex.png")
    val str = "123456789ABCDEF0"
    // 16 спрайтов букв и цифр для шестнадцатиричной печати.
    val glyphs = HashMap<Byte, Sprite>(16)


    fun installGlyphs() {
        var x = 0
        var y = 0
        str.forEach { c ->
            val sprite = Sprite(texture)
            sprite.setBounds(0f, 0f, 8f, 8f)
            sprite.setRegion(
                TextureRegion(
                    texture, x, y, 8, 8
                             )
                            )
            glyphs[c.code.toByte()] = sprite
            x += 8
            if (x >= 32) {
                x = 0
                y += 8
            }
        }
    }

    fun draw(batch: SpriteBatch, bytes: ByteArray?) {
        if (bytes == null) return
        var y = Gdx.graphics.height.toFloat() - TOP_HEIGHT - 8
        val width = Gdx.graphics.width.toFloat()
        var gId = 0
        hexFirstByteRectangle.set(HEX_ADDRESS_WIDTH + HEX_WIDTH_SPACE - 4, y - 4, 24f, 16f)
        hexLastByteRectangle.width = hexFirstByteRectangle.width
        hexLastByteRectangle.height = hexFirstByteRectangle.height
        hexLastByteRectangle.x = hexFirstByteRectangle.x + HEX_LABEL_WIDTH * (hexWidth - 1)
        hexLastByteRectangle.y = hexFirstByteRectangle.y - HEX_LABEL_HEIGHT * (hexHeight - 1)
        repeat(hexHeight) { yId ->
            var x = HEX_ADDRESS_WIDTH + HEX_WIDTH_SPACE
            repeat(hexWidth) { xId ->
                glyphs[bytes[gId]]?.setPosition(x, y)
                glyphs[bytes[gId]]?.draw(batch)
                gId++
                glyphs[bytes[gId]]?.setPosition(x + HEX_WIDTH_SPACE, y)
                glyphs[bytes[gId]]?.draw(batch)
                gId++
                x += 24f
            }
            y -= 16f
        }

    }

    fun dispose() {

    }

}
