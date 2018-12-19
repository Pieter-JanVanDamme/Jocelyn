package pjvandamme.be.jocelyn.Domain

import java.time.LocalDateTime

class Jotting(val creationTime: LocalDateTime, var content: String, var mentions: Map<String, Relation>) {

}