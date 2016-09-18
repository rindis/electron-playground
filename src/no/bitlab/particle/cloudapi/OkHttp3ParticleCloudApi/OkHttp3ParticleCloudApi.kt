package no.bitlab.particle.cloudapi.OkHttp3ParticleCloudApi

import no.bitlab.particle.cloudapi.ParticleCloudApi
import no.bitlab.particle.cloudapi.ParticleCloudApi.*
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class OkHttp3ParticleCloudApi(val baseUrl: String) : ParticleCloudApi {
  override fun callFunction(deviceId: String, accessToken: String, functionName: String, arguments: String): FunctionResponse {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val requestBody = RequestBody.create(JSON, "{\"arg\": \"$arguments\"}")
    val response = post(accessToken, "$deviceId/$functionName", requestBody)
    val returnData = JSONObject(response.body().string())
    val id = returnData.getString("id")
    val lastApp = returnData.getString("last_app")
    val connected = returnData.getBoolean("connected")
    val returnValue = returnData.getInt("return_value")
    
    return FunctionResponse(id, lastApp, connected, returnValue)
  }
  
  override fun getVariable(deviceId: String, accessToken: String, variableName: String): GetVariableResponse {
    val response = get(accessToken, "$deviceId/$variableName")
    val returnData = JSONObject(response.body().string())
    val result = returnData.get("result")
    val name = returnData.getString("name")
    val cmd = returnData.getString("cmd")
    val lastApp = returnData.getJSONObject("coreInfo").getString("last_app")
    val connected = returnData.getJSONObject("coreInfo").getBoolean("connected")
    val lastHeard = returnData.getJSONObject("coreInfo").getString("last_heard")
    val lastHandShakeAt = returnData.getJSONObject("coreInfo").getString("last_handshake_at")
    val productId = returnData.getJSONObject("coreInfo").getInt("product_id")
    val id = returnData.getJSONObject("coreInfo").getString("deviceID")
    
    return GetVariableResponse(result.toString(), name, cmd, lastApp, connected, lastHeard, lastHandShakeAt, productId, id)
  }
  
  override fun addDeviceEventListener(deviceId: String, accessToken: String, eventPrefix: String, event: (EventData) -> Unit) {
    thread() {
      val response = get(accessToken, "$deviceId/events/$eventPrefix")
      val source = response.body().source()
      while (!source.exhausted()) {
        val line = source.readUtf8Line()
        if (line.startsWith("event:")) {
          val name = line.split(":")[1].trim()
          val eventData = JSONObject(source.readUtf8Line().split("\\s+".toRegex())[1])
          val data = eventData.getString("data")
          val ttl = eventData.getString("ttl")
          val publishedAt = eventData.getString("published_at")
          val id = eventData.getString("coreid")
          
          event(EventData(name, data, ttl, publishedAt, id))
        }
      }
    }
  }
  
  override fun uploadFirmware(deviceId: String, accessToken: String, sourceFile: File): UploadFirmwareResponse {
    val mediaType = MediaType.parse("application/octetstream")
    val requestBody = MultipartBody.Builder()
      .setType(MultipartBody.FORM)
      .addFormDataPart("file", "firmware.bin", RequestBody.create(mediaType, sourceFile))
      .addFormDataPart("file_type", "binary")
      .build()
    val response = put(accessToken, deviceId, requestBody)
    val returnData = JSONObject(response.body().string())
    val id = returnData.getString("id")
    val status = returnData.getString("status")
    
    return UploadFirmwareResponse(id, status)
  }
  
  private fun post(accessToken: String, resource: String, requestBody: RequestBody): Response {
    val request = Request.Builder()
      .header("Authorization:", "Bearer " + accessToken)
      .url("$baseUrl/$resource")
      .post(requestBody)
      .build()
    
    return OkHttpClient().newCall(request).execute()
  }
  
  private fun put(accessToken: String, resource: String, requestBody: MultipartBody): Response {
    val request = Request.Builder()
      .header("Authorization:", "Bearer " + accessToken)
      .url("$baseUrl/$resource")
      .put(requestBody)
      .build()
    
    return OkHttpClient().newCall(request).execute()
  }
  
  private fun get(accessToken: String, resource: String): Response {
    val request = Request.Builder()
      .header("Authorization:", "Bearer " + accessToken)
      .url("$baseUrl/$resource")
      .get()
      .build()
    val client = OkHttpClient()
      .newBuilder()
      .readTimeout(0, TimeUnit.SECONDS)
      .retryOnConnectionFailure(true)
      .build()
    
    return client.newCall(request).execute()
  }
}
