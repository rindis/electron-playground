package no.bitlab.particle.cloudapi.devices

import no.bitlab.particle.cloudapi.post
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

fun callFunction(deviceId: String, accessToken: String, functionName: String, arguments: String): Response {
  val JSON = MediaType.parse("application/json; charset=utf-8")
  val requestBody = RequestBody.create(JSON, "{\"arg\": \"$arguments\"}")
  
  val response = post(accessToken, "$deviceId/$functionName", requestBody)
  
  val returnData = JSONObject(response.body().string())
  val id = returnData.getString("id")
  val lastApp = returnData.getString("last_app")
  val connected = returnData.getBoolean("connected")
  val returnValue = returnData.getInt("return_value")
  return Response(id, lastApp, connected, returnValue)
}

data class Response(val id: String, val lastApp: String, val connected: Boolean, val returnValue: Int)