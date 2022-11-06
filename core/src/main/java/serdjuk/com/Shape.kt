package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

class Shape(private val view: Viewport, main:View) : ShapeRenderer() {

    private val cross = Cross(main)

    init {
        setAutoShapeType(true)
    }

    fun draw() {
        projectionMatrix = view.camera.combined
        Gdx.gl20.glEnable(GL20.GL_BLEND)
        begin()
        if (dump != null && dump!!.isNotEmpty()) {
            cross.draw(this)
        }
        color = Color.BROWN
        set(ShapeRenderer.ShapeType.Line)
        line(
            HEX_ADDRESS_WIDTH + HEX_WIDTH_SPACE / 2f,
            hexBounds.y,
            HEX_ADDRESS_WIDTH + HEX_WIDTH_SPACE / 2f,
            hexBounds.height + 4f
            )
        rect(hexBounds.x, hexBounds.y, hexBounds.width, hexBounds.height)
        color = Color.GREEN
        rect(
            hexFocusedByteRectangle.x, hexFocusedByteRectangle.y, hexFocusedByteRectangle.width, hexFocusedByteRectangle.height
            )
        rect(
            hexFocusedAddressRectangle.x,hexFocusedAddressRectangle.y,hexFocusedAddressRectangle.width,
            hexFocusedAddressRectangle.height
            )
        end()
        Gdx.gl20.glDisable(GL20.GL_BLEND)
    }

    fun update() {
        cross.update()
    }

}