package no.bitlab.particle.cloudapi.firmware

import no.bitlab.particle.cloudapi.put
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

fun uploadFirmware(deviceId: String, accessToken: String, sourceFile: File): Response {
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
  return Response(id, status)
}

data class Response(val id: String, val status: String)