package com.example.awsssm

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Function

@SpringBootApplication
class AwsSsmApplication {

    @Bean
    fun readExternalProperty(@Value("\${test.property:fallback}") value: String) = ApplicationRunner {
        println("Got $value from properties")
    }

    @Bean
    fun stupidFunction(): Function<Flux<Message<String>>, Flux<Message<String>>> = Function {
        it
    }

}

fun main(args: Array<String>) {
    runApplication<AwsSsmApplication>(*args)
}
