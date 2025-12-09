package com.example.lista3z2.data

class SerialNumberRepository {

    fun verifySerialNumber(serial: String): Boolean {
        Thread.sleep(2500)
        return serial.startsWith("SN-") && serial.length == 10
    }
}


