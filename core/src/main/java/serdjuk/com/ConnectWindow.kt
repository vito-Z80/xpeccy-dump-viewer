package serdjuk.com

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField

class ConnectWindow(stage: Stage) : VisDialog("") {
    val dot = ".............................."
    init {
        val requestMessage = try {
            configFile.split("\n").filter { it.contains("xpeccy request", false) }[0]
        } catch (_: Exception) {
            Error.showConfigFileError(stage)
            ""
        }
        if (requestMessage.isNotEmpty()) {
            println(requestMessage)
        }
    }

    private val confirmLabel = VisLabel(dot)
    private var portValue: Int? = tcpPort
    private var addressValue: Int? = 0

    private val portInput = VisTextField("$tcpPort").also {
        it.maxLength = 5
        it.setTextFieldListener { textField, _ ->
            portValue = textField.text.tryHex(5)
            if (portValue != null) {
                tcpPort = portValue!!
            }
        }

        it.textFieldFilter = VisTextField.TextFieldFilter { _, c ->
            c.toString().matches(regHex)
        }
        it.setAlignment(Align.center)
    }

    private val requestInput = VisTextField(addressValue.toString()).also {
        it.maxLength = 5
        it.setTextFieldListener { textField, _ ->
            addressValue = textField.text.tryHex(5)
        }

        it.textFieldFilter = VisTextField.TextFieldFilter { _, c ->
            c.toString().matches(regHex)
        }
        it.setAlignment(Align.center)
    }

    private fun connectButton(): VisTextButton {
        val b = VisTextButton(MESSAGE_CONNECT)
        b.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (event?.target == b) {
                    if (Soc.connect()) {
                        confirmLabel.setText(dot)
                        startAddress = addressValue ?: 0
                        hide()
                        isDialogShow = false
                    } else {
                        confirmLabel.setText("No connection. Try again.")
                    }
                }
            }
        })
        return b
    }

    init {
//        closeOnEscape()
//        addCloseButton()

        contentTable.add("Connection dialog").padLeft(64f).padRight(64f).center().expand().row()
        contentTable.add("").row()
        contentTable.add("PORT:").row()
        contentTable.add(portInput).row()
        contentTable.add("Start dump address:").row()
        contentTable.add(requestInput).row()
        contentTable.add(connectButton()).row()
        contentTable.add("").row()
        contentTable.add(confirmLabel).row()
        setSize(200f, 200f)
        addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.ESCAPE) {
                    confirmLabel.setText(dot)
                    hide()
                    isDialogShow = false
                }
                if (keycode == Input.Keys.ENTER) {
                    if (Soc.connect()) {
                        confirmLabel.setText(dot)
                        startAddress = addressValue ?: 0
                        hide()
                        isDialogShow = false
                    } else {
                        confirmLabel.setText("No connection. Try again.")
                    }
                }

                return super.keyDown(event, keycode)
            }
        })

    }
}