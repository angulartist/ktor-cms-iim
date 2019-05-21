package belabes.mohamed.cms

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TestAdd {
    @Test
    @DisplayName("Testing sum")
    fun testAdd() { assertEquals(42, Integer.sum(19, 23)) }
}