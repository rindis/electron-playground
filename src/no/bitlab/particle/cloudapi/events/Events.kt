package no.bitlab.particle.cloudapi.events

import no.bitlab.particle.cloudapi.get
import org.json.JSONObject
import kotlin.concurrent.thread

fun addDeviceEventListener(deviceId: String, accessToken: String, eventPrefix: String, event: (eventDataData: EventData) -> Unit) {
  thread() {
    val response = get(accessToken, "$deviceId/events/$eventPrefix")
    val source = response.body().source()
    while (!source.exhausted()) {
      val line = source.readUtf8Line()
      if (line.startsWith("event:")) {
        val name = line.split("\\s+".toRegex())[1]
        val eventData = JSONObject(source.readUtf8Line().split("\\s+".toRegex())[1])
        val data = eventData.getString("data")
        val ttl = eventData.getString("ttl")
        val publishedAt = eventData.getString("published_at")
        val id = eventData.getString("coreid")
        
        event(EventData(name, data, ttl, publishedAt, deviceId))
      }
    }
  }
}

data class EventData(
  val name: String,
  val data: String,
  val ttl: String,
  val publishedAt: String,
  val deviceId: String)