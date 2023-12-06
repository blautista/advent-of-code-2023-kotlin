package day5

import readFile

data class ConversionMap(val destinationStart: Long, val sourceStart: Long, val range: Long)
data class ConversionSection(val name: String, val maps: List<ConversionMap>)
data class Almanac(val seeds: List<Long>, val sections: List<ConversionSection>)

fun parseMap(section: String): ConversionSection {
    val lineSplit = section.lines()

    val sectionName = lineSplit.first().replace(
        ":", ""
    )

    val sectionMaps = lineSplit.drop(1).map { line ->
        val nums = line.split(' ').map { it.toLong() }
        ConversionMap(nums[0], nums[1], nums[2])
    }

    return ConversionSection(sectionName, sectionMaps)
}

fun parseInput(input: String): Almanac {
    val lines = input.split("${System.lineSeparator()}${System.lineSeparator()}")
    val seeds = lines[0].split(' ').drop(1).map { it.toLong() }
    val sections = lines.drop(1).map { parseMap(it) }
    return Almanac(seeds, sections)
}

fun getSeedLocationNumber(sections: List<ConversionSection>, seed: Long): Long {
    var currentSeedValue = seed

    for (maps in sections.map { it.maps }) {
        for (map in maps) {
            if (currentSeedValue >= map.sourceStart && currentSeedValue <= map.sourceStart + map.range - 1) {
                currentSeedValue += map.destinationStart - map.sourceStart
                break
            }
        }
    }


    return currentSeedValue
}

fun solve1(almanac: Almanac): Long = almanac.seeds.minOf { getSeedLocationNumber(almanac.sections, it) }

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day5\\input.txt")

    val almanac = parseInput(inputString)
    
    print(solve1(almanac))
}