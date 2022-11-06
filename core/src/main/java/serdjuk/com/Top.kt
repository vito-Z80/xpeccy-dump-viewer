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
    val hexFocusedLabel = VisLabel()
    val hexAddressContentLabel = VisLabel()
    val connectButton = TextButton(MESSAGE_CONNECT, VisUI.getSkin())


    val cor = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            delay(40)
            width = Gdx.graphics.width.toFloat()
            height = TOP_HEIGHT.toFloat()
            setPosition(0f, Gdx.graphics.height.toFloat() - TOP_HEIGHT + 4f)
            hexWidthLabel.setText(hexWidth)
            hexHeightLabel.setText(hexHeight)
            hexFocusedLabel.setText("${hexFocusedAddress.toString(16)}/${String.format("%05d", hexFocusedAddress)}")
//            if (dump != null)
            val id = (hexFocusedAddress - startAddress) * 2
            if (id >= 0 && id < hexWidth * hexHeight * 2) {
                val value = "${dump?.get(id)?.toInt()?.toChar() ?: "?"}${dump?.get(id + 1)?.toInt()?.toChar() ?: "?"}"
                hexAddressContentLabel.setText(
                    "${value}/${
                        if (!value.contains("?")) String.format(
                            "%03d", value.toInt(16)
                                                               ) else "??"
                    }"
                                              )
            }

            connectButton.setText(if (dump == null || dump!!.isEmpty()) MESSAGE_CONNECT else MESSAGE_DISCONNECT)
        }
    }


    init {
        align(Align.topLeft)
        width()
        connectButton()
        row()

        height()

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