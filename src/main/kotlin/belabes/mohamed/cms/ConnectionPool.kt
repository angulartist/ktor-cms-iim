package belabes.mohamed.cms

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentLinkedQueue

class ConnectionPool(private val url: String, private val user: String, private val password: String) {
    /* Connection pool */
    private val queue = ConcurrentLinkedQueue<Connection>()

    /**
     * If no connection in the pool, return a new one, otherwise return the latest released connection
     */
    fun getConnection(): Connection = queue.poll() ?: DriverManager.getConnection(url, user, password)

    /**
     * Add back the released connection to the pool
     */
    fun releaseConnection(c: Connection): Boolean = queue.add(c)

    /**
     * Get connection pool
     */
    fun getPool(): ConcurrentLinkedQueue<Connection> = queue

    inline fun useConnection(f :(Connection) -> Unit) {
        val connection = getConnection()

        try {
            f(connection)
        } finally {
            releaseConnection(connection)
        }
    }
}