package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import kotlin.math.floor

class Cross(private val main: View) {

    val vertical = Rectangle()
    val horizontal = Rectangle()


    fun update() {

        // TODO реализовать полное управление:
        //  курсором со сдвигом адресов вверх/вних. При движении влево вправо переход на противоположную сторону
        //  колесо мыши до пределов
        //  педжап/педждовн
        //  кнопко интёр на ячейке памяти для замены байта
        //  реализовать нажатие кнопок без джаст, с задержкой, где первое нажатие с более длительной задержкой.

        hexBounds.set(
            2f, 4f, HEX_ADDRESS_WIDTH + 24f * hexWidth + 3f, Gdx.graphics.height - TOP_HEIGHT
                     )

        if (point != exPoint && hexBounds.contains(point)) {
            // перемещение мышкой
            hexFocusedByteRectangle.set(
                floor((point.x - hexFirstByteRectangle.x) / hexFirstByteRectangle.width) * hexFirstByteRectangle.width + hexFirstByteRectangle.x,
                floor((point.y - hexFirstByteRectangle.y) / hexFirstByteRectangle.height) * hexFirstByteRectangle.height + hexFirstByteRectangle.y,
                hexFirstByteRectangle.width,
                hexFirstByteRectangle.height
                                       )
        } else {
            // перемещение курсором когда мышка не подвижна
            cursor()
        }


        getHexFocusAddress()


        clamp()
        setCross()
    }

    private fun clamp() {
        hexFocusedByteRectangle.x = MathUtils.clamp(
            hexFocusedByteRectangle.x, hexFirstByteRectangle.x, hexFirstByteRectangle.x + (hexWidth - 1) * 24f
                                                   )
        hexFocusedByteRectangle.y = MathUtils.clamp(
            hexFocusedByteRectangle.y, hexFirstByteRectangle.y - (hexHeight - 1) * 16f, hexFirstByteRectangle.y
                                                   )
        // корректрировка адреса
        startAddress = MathUtils.clamp(startAddress, 0, 65536 - hexWidth * hexHeight)
    }

    private fun cursor() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            hexFocusedAddress--
            hexFocusedByteRectangle.x -= 24f
            if (hexFocusedByteRectangle.x < HEX_ADDRESS_WIDTH && hexFocusedByteRectangle.y == hexFirstByteRectangle.y) {
                startAddress--
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            hexFocusedAddress++
            hexFocusedByteRectangle.x += 24f
            if (hexFocusedByteRectangle.x > hexLastByteRectangle.x && hexFocusedByteRectangle.y == hexLastByteRectangle.y) {
                startAddress++
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            hexFocusedAddress -= hexWidth
            hexFocusedByteRectangle.y += 16f
            if (!hexBounds.overlaps(hexFocusedByteRectangle)) {
                startAddress -= hexWidth
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            hexFocusedAddress += hexWidth
            hexFocusedByteRectangle.y -= 16f
            if (!hexBounds.overlaps(hexFocusedByteRectangle)) {
                startAddress += hexWidth
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN)) {
            startAddress += hexWidth * hexHeight
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP)) {
            startAddress -= hexWidth * hexHeight
        }
    }

    // установка данных для отрисовки креста
    private fun setCross() {
        vertical.set(
            hexFocusedByteRectangle.x, hexBounds.y, hexFocusedByteRectangle.width, hexBounds.height
                    )
        horizontal.set(
            2f, hexFocusedByteRectangle.y, hexBounds.width, hexFocusedByteRectangle.height
                      )
    }

    fun draw(shape: ShapeRenderer) {
        shape.color = Color(0.1f, 0.5f, 0.4f, 0.4f)
        shape.set(ShapeRenderer.ShapeType.Filled)
        shape.rect(vertical.x, vertical.y, vertical.width, vertical.height)
        shape.rect(horizontal.x, horizontal.y, horizontal.width, horizontal.height)
    }

}