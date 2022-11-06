package serdjuk.com

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlinx.coroutines.*


const val TOP_HEIGHT = 64f          //
const val HEX_ADDRESS_WIDTH = 32f
const val HEX_BYTE_WIDTH = 16f
const val HEX_LABEL_HEIGHT = 16f
const val HEX_LABEL_WIDTH = 24f
const val HEX_WIDTH_SPACE = 8f
const val HEX_HEIGHT_SPACE = 8f

const val MESSAGE_CONNECT = "Connect"
const val MESSAGE_DISCONNECT = "Disconnect"

var isConnect = false


var screenWidth = 0
var screenHeight = 0

var startAddress = 16384
var hexWidth = 0
var hexHeight = 0

var tcpPort = 30000

var hexFocusedAddress = -1
val point = Vector2()
val exPoint = Vector2()

val hexFocusedByteRectangle = Rectangle()
val hexFirstByteRectangle = Rectangle()
val hexLastByteRectangle = Rectangle()

val hexFocusedAddressRectangle = Rectangle()
val hexFirstAddressRectangle = Rectangle()
val hexLastAddressRectangle = Rectangle()

val hexBounds = Rectangle()

var dump: ByteArray? = null
var wheel = 0f

var isDialogShow = false        // когда открыто какое то диалоговое окно

//---------------------------------------------------------------------------------------
const val xpeccyRequestInfo = "dumpraw addr length"

//---------------------------------------------------------------------------------------
var xpeccyRequest = ""

//---------------------------------------------------------------------------------------
val configFile = Gdx.files.internal("config.txt").readString()


//---------------------------------------------------------------------------------------