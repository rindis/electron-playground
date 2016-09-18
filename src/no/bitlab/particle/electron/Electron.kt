package no.bitlab.particle.electron

import no.bitlab.particle.cloudapi.OkHttp3ParticleCloudApi.OkHttp3ParticleCloudApi

fun main(args: Array<String>) {
  val cloudApi = OkHttp3ParticleCloudApi("https://api.particle.io/v1/devices")
  
  cloudApi.addDeviceEventListener(deviceId, accessToken, "distance") {
    println("it.name = ${it.name}")
    println("it.data = ${it.data}")
  }
  
  val response = cloudApi.getVariable(deviceId, accessToken, "batCharge")
  println("response.name = ${response.name}")
  println("response.value = ${response.value}")
}
