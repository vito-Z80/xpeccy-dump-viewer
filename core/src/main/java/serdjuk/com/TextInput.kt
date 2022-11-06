package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI

open class TextInput(text: String, font: BitmapFont, size: Vector2) : TextField(text, VisUI.getSkin()) {

    var value: Int? = null
    var changeAddress = -1
    var hexLength = 3

    init {
        style.font = font
        style.messageFont = font
        style.font.data.descent = -9f    // смещает zx font по Y
        maxLength = 3
        this.setSize(size.x, size.y)
        isVisible = false
        alignment = Align.center

        textFieldFilter = TextFieldFilter { _, c ->
            c.toString().matches(regHex)
        }

        /*
            Текст может быть конвертировать в байт если:
                1) состоит из одних цифр (до 3 цифр)
                2) состоит из цифр и букв (до 2 значений)
                3) сщстоит из решетки и цифр с буквами (до 3 значений при условии что решетка идет первой)
         */
        this.setTextFieldListener { textField, _ ->
            value = textField.text.tryHex(hexLength)
        }

        this.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.ESCAPE) {
                    reset()
                }
                return super.keyDown(event, keycode)
            }
        })


    }

    open fun input() {
        if (!isConnect) return

//        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            isVisible = changeAddress == hexFocusedAddress
//            if (isVisible) {
//                if (value != null) {
//                    Soc.xpeccyPoke(changeAddress, value!!)
//                }
//                reset()
//            } else {
//                isVisible = true
//                stage.keyboardFocus = this
//                setPosition(hexFocusedRectangle.x, hexFocusedRectangle.y)
//                changeAddress = getFocusAddress()
//            }
//        }

        if (hexBounds.contains(point) && point.x >= hexFirstByteRectangle.x) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                if (isVisible) {
                    if (value != null) {
                        if (changeAddress < 0) changeAddress = hexFocusedAddress
                        Soc.xpeccyPoke(changeAddress, value!!)
                    }
                    reset()
                } else {
                    isVisible = true
                    disableFocusExcept()
                    stage.keyboardFocus = this
                    setPosition(hexFocusedByteRectangle.x, hexFocusedByteRectangle.y)
                    changeAddress = getHexFocusAddress()
                }
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && point.x > HEX_ADDRESS_WIDTH) {
            if (isVisible) {
                if (value != null) {
                    if (changeAddress < 0) changeAddress = hexFocusedAddress
                    Soc.xpeccyPoke(changeAddress, value!!)
                }
                reset()
            } else {
                isVisible = true
                disableFocusExcept()
                stage.keyboardFocus = this
                setPosition(hexFocusedByteRectangle.x, hexFocusedByteRectangle.y)
                changeAddress = getHexFocusAddress()
            }
        }
    }

    fun reset() {
        stage.keyboardFocus = null
        setText("")
        changeAddress = -1
        value = null
        isVisible = false
    }

    /*
        Отключить фокус всем TextInput кроме этого.
     */
    fun disableFocusExcept() {
        stage.root.children.filter { it is TextInput && it != this }.forEach { it.isVisible = false }
    }

    override fun act(delta: Float) {
        if (!isDialogShow) {
            input()
        }
        super.act(delta)
    }
}
