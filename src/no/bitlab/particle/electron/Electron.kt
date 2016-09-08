package no.bitlab.particle.electron

import no.bitlab.particle.cloudapi.devices.callFunction

fun main(args: Array<String>) {
  callFunction(deviceId, accessToken, "led", "off")
/*  val sourceFile = File("C:\\Users\\Martin\\Software Development\\GitHub\\particle-electron\\electron_firmware_1473369443776.bin")
  uploadFirmware(resource, accessToken, sourceFile)*/
}
