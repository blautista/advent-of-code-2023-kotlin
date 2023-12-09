import java.io.File

fun <T> MutableList<T>.prepend(element: T) {
    add(0, element)
}

fun readFile(path: String) = File(path).bufferedReader().use { it.readText() }

tailrec fun gcd(a: Long, b: Long): Long = if (b <= 0) a else gcd(b, a % b)

fun gcd(vararg nums: Long): Long = nums.drop(1).fold(nums[0]) { prev, curr -> gcd(prev, curr) }

fun lcm(a: Long, b: Long): Long = a * (b / gcd(a, b))

fun lcm(vararg nums: Long): Long = nums.drop(1).fold(nums[0]) { prev, curr -> lcm(prev, curr) }