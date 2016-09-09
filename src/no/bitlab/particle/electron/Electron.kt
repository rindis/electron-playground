package no.bitlab.particle.electron

import no.bitlab.particle.cloudapi.events.addDeviceEventListener

fun main(args: Array<String>) {
//  callFunction(deviceId, accessToken, "led", "off")
/*  val sourceFile = File("C:\\Users\\Martin\\Software Development\\GitHub\\particle-electron\\electron_firmware_1473369443776.bin")
  uploadFirmware(resource, accessToken, sourceFile)*/
  
  addDeviceEventListener(deviceId, accessToken, "test") {
    println("it.name = ${it.name}")
    println("it.data = ${it.data}")
    println("it.ttl = ${it.ttl}")
    println("it.publishedAt = ${it.publishedAt}")
    println("it.deviceId = ${it.deviceId}")
  }
}
