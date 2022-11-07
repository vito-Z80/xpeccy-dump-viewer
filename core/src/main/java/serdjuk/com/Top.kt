package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Top(private val font: BitmapFont, private val stage2: Stage) : VisTable() {

    val pad = 4f

    //    val info = Dialog("Info",VisUI.getSkin())
    val connectWindow = ConnectWindow(stage2)
    val address = VisTextField("0")

    val hexWidthLabel = VisLabel()
    val hexHeightLabel = VisLabel()
    val focusedAddressLabelHex = VisLabel().also { it.setAlignment(Align.right) }
    val focusedAddressLabelDec = VisLabel()
    val focusContentLabelHex = VisLabel().also { it.setAlignment(Align.right) }
    val focusContentLabelDec = VisLabel()
    val connectButton = TextButton(MESSAGE_CONNECT, VisUI.getSkin())


    val cor = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            delay(40)
            width = Gdx.graphics.width.toFloat()
            height = TOP_HEIGHT.toFloat()
            setPosition(0f, Gdx.graphics.height.toFloat() - TOP_HEIGHT + 4f)
            hexWidthLabel.setText(hexWidth)
            hexHeightLabel.setText(hexHeight)
            focusedAddressLabelDec.setText(if (hexFocusedAddress < 0) "?????" else String.format("%05d", hexFocusedAddress))
            focusedAddressLabelHex.setText("#${if (hexFocusedAddress < 0) "????" else String.format("%04X", hexFocusedAddress)}")
            val id = (hexFocusedAddress - startAddress) * 2
            if (id >= 0 && id < hexWidth * hexHeight * 2) {
                val value = "${dump?.get(id)?.toInt()?.toChar() ?: "?"}${dump?.get(id + 1)?.toInt()?.toChar() ?: "?"}"
                focusContentLabelHex.setText("#${value}")
                focusContentLabelDec.setText(if (value.contains("?"))"???" else String.format("%03d", value.toInt(16)))
            }

            connectButton.setText(if (dump == null || dump!!.isEmpty()) MESSAGE_CONNECT else MESSAGE_DISCONNECT)
        }
    }


    init {
        align(Align.topLeft)
        width()
        focusedAddress()
        connectButton()
        row()
        height()
        focusedAddressContent()

    }


    private fun focusedAddress() {
        add(focusedAddressLabelHex).right().width(64f)
        add("|").center().padLeft(8f).padRight(8f)
        add(focusedAddressLabelDec).left().width(64f)
    }

    private fun focusedAddressContent() {
        add(focusContentLabelHex).right().width(64f)
        add("|").center().padLeft(8f).padRight(8f)
        add(focusContentLabelDec).left().width(64f)
    }

    private fun width() {
        add("W:").padLeft(pad).left()
        add(hexWidthLabel).right().padLeft(pad).fillX()
    }

    private fun height() {
        add("H:").padLeft(pad).left()
        add(hexHeightLabel).right().padLeft(pad).fillX()
    }


    private fun connectButton() {
        connectButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (event?.target == connectButton) {
                    if (!isConnect) {
                        connectWindow.setPosition(
                            Gdx.graphics.width / 2 - connectWindow.width / 2,
                            Gdx.graphics.height / 2 - connectWindow.height / 2,
                                                 )
                        connectWindow.show(stage2)
                        isDialogShow = true
                    } else {
                        Soc.disconnect()
                    }
                }
            }
        })
        add(connectButton).padLeft(16f)
    }
}