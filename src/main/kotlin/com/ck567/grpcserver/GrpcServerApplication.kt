package com.ck567.grpcserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcServerApplication

fun main(args: Array<String>) {
	runApplication<GrpcServerApplication>(*args)
	val port = System.getenv("PORT")?.toInt() ?: 9090
	val server = HelloServer(port)
	server.start()
	server.blockUntilShutdown()
}
