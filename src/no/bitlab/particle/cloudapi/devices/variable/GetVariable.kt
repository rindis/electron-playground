package no.bitlab.particle.cloudapi.devices.variable

import no.bitlab.particle.cloudapi.get
import org.json.JSONObject

fun getVariable(deviceId: String, accessToken: String, variableName: String): Response {
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
  
  return Response(result.toString(), name, cmd, lastApp, connected, lastHeard, lastHandShakeAt, productId, id)
}

data class Response(
  val value: String,
  val name: String,
  val cmd: String,
  val lasApp: String,
  val connected: Boolean,
  val lastHeard: String,
  val lastHandShakeAt: String,
  val productId: Int,
  val deviceId: String)