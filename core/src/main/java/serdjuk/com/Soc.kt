package serdjuk.com

import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException

object Soc {

//    val c = CoroutineScope(Dispatchers.IO).launch {
//        val request = "dump ${start.toString(16)} ${length.toString(16)}".toByteArray()
//    }


    var socket: Socket? = null
    var istr: InputStream? = null
    var ostr: OutputStream? = null

    var count = 0

    var greetings: String = ""


    fun disconnect() {
        socket = null
        istr = null
        ostr = null
        isConnect = false
        dump = null
    }

    fun connect(): Boolean {
        val r = try {
            socket = Socket()
            socket?.connect(InetSocketAddress("127.0.0.1", tcpPort))
            istr = socket?.getInputStream()
            ostr = socket?.getOutputStream()
//            val request = "dumpraw ${0} ${hexWidth * hexHeight}\r\n".toByteArray()
            val request = "".toByteArray()
            ostr?.write(request)
            istr?.bufferedReader()?.readLine().let {
                it?.let { g -> greetings = g }
                println("GREETINGS: $greetings")
            }
//            val result = istr?.bufferedReader()?.readLine()?.toByteArray()
//            println(result?.joinToString())
//            dump = result
            true
        } catch (_: Exception) {
            socket = null
            false
        }

        println("WITH: $r")


        isConnect = r
        return r
    }


    /*
    value = Dec следом конверт в Hex для отправки
     */
    fun xpeccyPoke(address: Int, value: Int) {
        val h = Integer.toHexString(value)
        try {
            ostr?.write("poke #${address.toString(16)} #${h}".toByteArray())
            istr?.bufferedReader()?.readLine().let { println("$it: poke #${address.toString(16)} #${h}".uppercase()) }
        } catch (_: SocketException) {
            disconnect()
            println("Connection reset by peer: socket write error")
        }
    }

    fun xpeccyDump(start: Int, length: Int): ByteArray? {
        if (length < 0 && socket == null) return null
//        println("${socet.isConnected} | ${socet.isClosed} | ${socet.isBound} | ${socet.isInputShutdown} | ${socet.isOutputShutdown}")
        if (socket != null && !socket!!.isConnected) {
            try {
                socket?.connect(InetSocketAddress("127.0.0.1", 30000))
                istr = socket?.getInputStream()
                ostr = socket?.getOutputStream()

                istr?.bufferedReader()?.readLine().let {
                    it?.let { g -> greetings = g }
                    println("GREETINGS: $greetings")
                }
            } catch (_: Exception) {
            }
        } else {
            try {
                val request = "dumpraw ${start.toString(16)} ${length.toString()}\r\n".toByteArray()
                ostr?.write(request)
                val result = istr?.bufferedReader()?.readLine()?.toByteArray()
                if (result == null || result.isEmpty()) {
//                    socet.connect(InetSocketAddress("127.0.0.1", 30000))
//                    istr = socet.getInputStream()
//                    ostr = socet.getOutputStream()
                }
                return result
            } catch (_: Exception) {
            }
        }


        return null
    }

    fun dispose() {
        if (socket != null && !socket!!.isClosed) {
            socket?.close()
        }
        istr?.close()
        ostr?.close()
    }
}