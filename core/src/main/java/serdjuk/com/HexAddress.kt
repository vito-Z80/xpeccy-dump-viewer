package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage

class HexAddress(private val font: BitmapFont, private val stage: Stage) {

    val ti = object : TextInput("", font, Vector2(32f, 16f)) {
        init {
            isVisible = false
            this@HexAddress.stage.addActor(this as TextInput)
            maxLength = 5
            hexLength = 5
        }

        override fun input() {
            if (!isConnect) return
            if (hexBounds.contains(point) && point.x < HEX_ADDRESS_WIDTH) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    if (isVisible) {
                        if (value != null) {
                            val y = (hexHeight - hexFocusedAddressRectangle.y / HEX_LABEL_HEIGHT).toInt()
                            startAddress = value!! - y * hexWidth
                        }
                        reset()
                    } else {
                        isVisible = true
                        disableFocusExcept()
                        stage.keyboardFocus = this
                        setPosition(hexFocusedAddressRectangle.x, hexFocusedAddressRectangle.y)
                        changeAddress = getHexFocusAddress()
                    }
                }
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && point.x < HEX_ADDRESS_WIDTH) {
                if (isVisible) {
                    if (value != null) {
                        val y = (hexHeight - hexFocusedAddressRectangle.y / HEX_LABEL_HEIGHT).toInt()
                        startAddress = value!! - y * hexWidth
                    }
                    reset()
                } else {
                    isVisible = true
                    disableFocusExcept()
                    stage.keyboardFocus = this
                    setPosition(hexFocusedAddressRectangle.x, hexFocusedAddressRectangle.y)
                    changeAddress = getHexFocusAddress()
                }
            }

        }

    }


    var list = listOf<String>()
    var hexW = hexWidth
    var hexH = hexHeight
    var bounds = List(128) { Rectangle() }

    fun update() {
        var pointInFocus = false
        hexFirstAddressRectangle.set(
            2f, Gdx.graphics.height.toFloat() - TOP_HEIGHT - 12, 34f, HEX_LABEL_HEIGHT
                                    )
        hexLastAddressRectangle.x = hexFirstAddressRectangle.x
        hexLastAddressRectangle.y = hexFirstAddressRectangle.y - HEX_LABEL_HEIGHT * (hexHeight - 1)
        hexLastAddressRectangle.width = hexFirstAddressRectangle.width
        hexLastAddressRectangle.height = hexFirstAddressRectangle.height
        repeat(hexHeight) {
            bounds[it].set(
                hexFirstAddressRectangle.x,
                hexFirstAddressRectangle.y - it * HEX_LABEL_HEIGHT,
                hexFirstAddressRectangle.width,
                hexFirstAddressRectangle.height
                          )
            if (bounds[it].contains(point)) {
                hexFocusedAddressRectangle.set(bounds[it])
                pointInFocus = true
            }
        }


        // TODO не делать обновление каждый кадр, стринги жрут память.
//        if (hexW != hexWidth || hexH != hexHeight || wheel != 0f) {
//            println("update")
        list = if (hexHeight > 0) {
            List<String>(hexHeight) {
                val s = startAddress + (it * hexWidth)
                String.format("%04X", s)
            }
        } else {
            listOf()
        }
//        }
        hexW = hexWidth
        hexH = hexHeight
    }

    fun draw(batch: SpriteBatch) {
        font.color = Color.LIGHT_GRAY
        repeat(hexHeight) {
            font.draw(batch, list[it], bounds[it].x + 3f, bounds[it].y + 10f)
        }
    }

}