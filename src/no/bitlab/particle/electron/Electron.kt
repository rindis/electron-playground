package no.bitlab.particle.electron

import no.bitlab.particle.cloudapi.devices.variable.getVariable
import no.bitlab.particle.cloudapi.events.addDeviceEventListener

fun main(args: Array<String>) {
  addDeviceEventListener(deviceId, accessToken, "distance") {
    println("it.name = ${it.name}")
    println("it.data = ${it.data}")
  }
  
  val response = getVariable(deviceId, accessToken, "batCharge")
  println("response.name = ${response.name}")
  println("response.value = ${response.value}")
}
