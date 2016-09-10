package no.bitlab.particle.cloudapi

import okhttp3.*
import java.util.concurrent.TimeUnit

private val baseUrl = "https://api.particle.io/v1/devices"

fun post(accessToken: String, resource: String, requestBody: RequestBody): Response {
  val request = Request.Builder()
    .header("Authorization:", "Bearer " + accessToken)
    .url("$baseUrl/$resource")
    .post(requestBody)
    .build()
  
  return OkHttpClient().newCall(request).execute()
}

fun put(accessToken: String, resource: String, requestBody: MultipartBody): Response {
  val request = Request.Builder()
    .header("Authorization:", "Bearer " + accessToken)
    .url("$baseUrl/$resource")
    .put(requestBody)
    .build()
  
  return OkHttpClient().newCall(request).execute()
}

fun get(accessToken: String, resource: String): Response {
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