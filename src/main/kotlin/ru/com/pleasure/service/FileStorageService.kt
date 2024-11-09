package ru.com.pleasure.service

import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.nio.file.Paths

@Service
class FileStorageService {
    fun storeFile(file: FilePart): Mono<String> {
        val filePath = "/control/${file.filename()}"
        return file.transferTo(Paths.get(filePath)).then(Mono.just(filePath))
    }
}