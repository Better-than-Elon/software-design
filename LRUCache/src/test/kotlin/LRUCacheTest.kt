import org.junit.Test
import kotlin.test.assertEquals

internal class LRUCacheTest {


    @Test(expected = AssertionError::class)
    fun illegalMaxSize() {
        LRUCache<Int, Int>(-1)
    }

    @Test
    fun simpleTest() {
        val cache = LRUCache<Int, Int>(2)
        val key = 10
        val value = 42
        assertEquals(null, cache.get(key))
        cache.put(key, value)
        assertEquals(value, cache.get(key))
    }

    @Test
    fun middleTest() {
        manyPuts(10)
        manyPuts(100)
        manyPuts(1000)
    }

    private fun manyPuts(capacity: Int) {
        val lru: LRUCache<Int, Int> = LRUCache(capacity)
        for (i in 0 until capacity) {
            assertEquals(null, lru.get(i))
            lru.put(i, 2 * i)
        }
        for (i in 0 until capacity) {
            val getVal = lru.get(i)!!
            assertEquals(2 * i, getVal)
        }
    }


    @Test
    fun singleSequantialTest() {
        val lru: LRUCache<Int, Int> = LRUCache(4)
        val putsList: List<Int> = listOf(0, 1, 2, 4, 2, 3, 5, 3, 4, 1, 6, 3, 6)
        for (element in putsList) {
            lru.put(element, element)
        }
        val listKeys: List<Int?> = listOf(null, 1, null, 3, 4, null, 6)
        for (i in listKeys.indices) {
            assertEquals(listKeys[i], lru.get(i))
        }
    }

    @Test
    fun multiSequentionalTest() {
        val lru: LRUCache<Int, Int> = LRUCache(4)
        validateState(
            lru, listOf(1, 4, 1, 2, 5, 7, 3, 7, 13, 26, 125, 74, 125, 7, 7, 7, 7), listOf(
                listOf(1),
                listOf(4, 1),
                listOf(1, 4),
                listOf(2, 1, 4),
                listOf(5, 2, 1, 4),
                listOf(7, 5, 2, 1),
                listOf(3, 7, 5, 2),
                listOf(7, 3, 5, 2),
                listOf(13, 7, 3, 5),
                listOf(26, 13, 7, 3),
                listOf(125, 26, 13, 7),
                listOf(74, 125, 26, 13),
                listOf(125, 74, 26, 13),
                listOf(7, 125, 74, 26),
                listOf(7, 125, 74, 26),
                listOf(7, 125, 74, 26),
                listOf(7, 125, 74, 26),
            )
        )
    }

    private fun validateState(lru: LRUCache<Int, Int>, puts: List<Int>, validate: List<List<Int>>) {
        for (i in puts.indices) {
            for (j in 0..i) {
                lru.put(puts[j], puts[j])
            }
            for (key in puts) {
                if (key in validate[i]) {
                    assertEquals(key, lru.get(key))
                } else {
                    assertEquals(null, lru.get(key))
                }
            }
        }
    }

    @Test
    fun extraTest() {
        val lru: LRUCache<Int, Int> = LRUCache(4)
        lru.put(1,1)
        lru.put(2,2)
        lru.put(3,3)
        lru.put(4,4)
        lru.put(5,5)
        //5->4->3->2
        assertEquals(null, lru.get(1))
        lru.get(5)
        lru.get(4)
        lru.get(3)
        lru.get(2)
        //2->3->4->5
        lru.put(1,1)
        //1->2->3->4
        assertEquals(null, lru.get(5))
    }
}