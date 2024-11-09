package ru.com.pleasure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PleasureApplication

fun main(args: Array<String>) {
    runApplication<PleasureApplication>(*args)
}
