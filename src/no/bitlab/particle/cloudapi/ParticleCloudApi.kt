package no.bitlab.particle.cloudapi

import java.io.File

interface ParticleCloudApi {
  fun callFunction(deviceId: String, accessToken: String, functionName: String, arguments: String): FunctionResponse
  fun getVariable(deviceId: String, accessToken: String, variableName: String): GetVariableResponse
  fun addDeviceEventListener(deviceId: String, accessToken: String, eventPrefix: String, event: (eventData: EventData) -> Unit)
  fun uploadFirmware(deviceId: String, accessToken: String, sourceFile: File): UploadFirmwareResponse
  
  data class EventData(val name: String,
                       val data: String,
                       val ttl: String,
                       val publishedAt: String,
                       val deviceId: String)
  
  data class FunctionResponse(val id: String,
                              val lastApp: String,
                              val connected: Boolean,
                              val returnValue: Int)
  
  data class GetVariableResponse(val value: String,
                                 val name: String,
                                 val cmd: String,
                                 val lasApp: String,
                                 val connected: Boolean,
                                 val lastHeard: String,
                                 val lastHandShakeAt: String,
                                 val productId: Int,
                                 val deviceId: String)
  
  data class UploadFirmwareResponse(val id: String,
                                    val status: String)
}