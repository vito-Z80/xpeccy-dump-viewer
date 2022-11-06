package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.widget.VisDialog

object Error {

    private val configFileError = VisDialog("Error").also {
        it.contentTable.add("The configuration file is corrupted.").padLeft(64f).padRight(64f).row()
        it.contentTable.add("The application will not work properly.")
    }

    fun showConfigFileError(stage: Stage) {
        configFileError.setPosition(
            Gdx.graphics.width / 2 - configFileError.width / 2,
            Gdx.graphics.height / 2 - configFileError.height / 2,
                                   )
        configFileError.show(stage)
        isDialogShow = true
    }
}