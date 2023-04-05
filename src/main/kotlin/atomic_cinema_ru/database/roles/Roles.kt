package atomic_cinema_ru.database.roles

import org.jetbrains.exposed.sql.Table

object Roles : Table("cinema.role") {
    private val id = Roles.integer("idrole").autoIncrement()
    private val name = Roles.varchar("namerole", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}