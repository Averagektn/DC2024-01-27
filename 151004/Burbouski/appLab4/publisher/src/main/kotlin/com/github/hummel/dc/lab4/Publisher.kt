package com.github.hummel.dc.lab4

import com.github.hummel.dc.lab4.controller.configureRouting
import com.github.hummel.dc.lab4.module.appModule
import com.github.hummel.dc.lab4.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.koin.ktor.plugin.Koin
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

//вводить в терминал
//docker network create kafkanet
//docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
//docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

private lateinit var producer: KafkaProducer<String, String>

fun main() {
	embeddedServer(Netty, port = 24110, module = Application::publisher).start(wait = true)
}

fun Application.publisher() {
	install(DoubleReceive)
	install(Koin) {
		dataModule.single<Connection> {
			Class.forName("org.postgresql.Driver")
			DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
		}
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
	configureKafka()
}

fun configureKafka() {
	val bootstrapServers = "localhost:9092"

	val producerProps = Properties()
	producerProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
	producerProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
	producerProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name

	producer = KafkaProducer<String, String>(producerProps)

	sendViaKafka("From Publisher: Connection established!")
}

fun sendViaKafka(message: String) {
	val topic = "app"
	val record = ProducerRecord<String, String>(topic, message)
	producer.send(record)
}