package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.VisLabel
import kotlin.random.Random

class View : Screen {

    val ui = VisUI.load()

    val hex = Hex()

    val batch = SpriteBatch()
    val cam = OrthographicCamera()
    val view = ScreenViewport(cam)
    val font = lazy { BitmapFont(Gdx.files.internal("zx_font.fnt"), Gdx.files.internal("zx_font.png"), false) }.value
    val shape = Shape(view, this)
    val stage = Stage(view, batch)
    val top = Top(font,stage)
    val hexAddress = HexAddress(font, stage)


    val ti = TextInput("", font, Vector2(24f, 16f))


    override fun show() {


        Gdx.input.inputProcessor = stage
        stageListener()
        stage.addActor(top)
        stage.addActor(ti)
        VisUI.getSkin().add("zx_font", font, BitmapFont::class.java)
        hex.installGlyphs()
    }

    val b = Random.nextBytes(hexWidth * hexHeight)
    override fun render(delta: Float) {

        screenWidth = Gdx.graphics.width
        screenHeight = Gdx.graphics.height
        hexWidth = (screenWidth - HEX_ADDRESS_WIDTH.toInt()) / 24
        hexHeight = (screenHeight - TOP_HEIGHT.toInt()) / 16

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)



        if (stage.root.children.filterIsInstance<VisDialog>().all { !it.isVisible }  && isConnect) {

            point.set(Gdx.input.x.toFloat(), screenHeight - Gdx.input.y.toFloat())
            dump = Soc.xpeccyDump(startAddress, hexWidth * hexHeight)
            hexAddress.update()
            shape.update()
            shape.draw()


            batch.projectionMatrix = view.camera.combined
            batch.begin()

            if (dump != null && dump!!.isNotEmpty()) {
                hexAddress.draw(batch)
            }
            hex.draw(batch, dump)
            batch.end()
        }




        stage.act(delta)
        stage.draw()


        wheel = 0f
        exPoint.set(point)
    }

    override fun resize(width: Int, height: Int) {
        view.update(width, height, true)

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {


    }

    override fun dispose() {
        VisUI.dispose()
        Soc.dispose()
        font.dispose()
    }


    private fun stageListener() {


        stage.addListener(object : InputListener() {

            override fun scrolled(event: InputEvent?, x: Float, y: Float, amountX: Float, amountY: Float): Boolean {
                startAddress += amountY.toInt() * hexWidth
                startAddress = MathUtils.clamp(startAddress, 0, 65536 - hexWidth * hexHeight)
                wheel = amountY
                return super.scrolled(event, x, y, amountX, amountY)
            }

        })


    }
}