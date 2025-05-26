# BDEngine-Parser

[![License](https://img.shields.io/badge/license-CC--BY--NC%204.0-blue)](https://creativecommons.org/licenses/by-nc/4.0/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-blue)](https://kotlinlang.org/)

> A Kotlin library to parse Minecraft `/summon` commands for display entities (BlockDisplay, TextDisplay, ItemDisplay) into structured models.

---

## Features

| Feature                   | Description                                                                                 |
|---------------------------|---------------------------------------------------------------------------------------------|
| **Command Parsing**       | Reads raw `/summon` commands and deserializes NBT blocks into Kotlin models.                |
| **BlockDisplay Support**  | Parses `block_display` entities, capturing block state names and properties.                |
| **TextDisplay Support**   | Parses `text_display` entities, extracting text components, styling, and layout properties. |
| **Passengers Hierarchy**  | Maintains order and nesting of passenger entities in the summon command.                    |
| **Transformation Arrays** | Captures full 4×4 matrix transformations for each display entity.                           |

---

## Installation

Add the library to your Gradle project:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.zornov.bdengine:parser:1.0.0"
}
```

---

## Usage Example

```kotlin
fun main() {
    val raw = "/summon block_display ~ ~ ~ {Passengers:[{id:\"minecraft:block_display\",...}]}"
    val parser = EntityParser()
    val result = parser.parseCommand(raw)

    println("Parsed ${result.passengers.size} passengers:")
    result.passengers.forEach { println(it) }
}
```

---

## Model Classes

### `CommandResult`

| Property     | Type                  | Description                                              |
|--------------|-----------------------|----------------------------------------------------------|
| `passengers` | `List<EntityDisplay>` | List of parsed display entities from the summon command. |

### `EntityDisplay` (sealed)

* **`BlockDisplay`**

    * `blockState.name: String`
    * `blockState.properties: Map<String, String>`
    * `transformation: FloatArray` (16 floats)

* **`TextDisplay`**

    * `components: List<TextComponent>`
    * `textOpacity: Int`
    * `background: Int`
    * `alignment: String`
    * `lineWidth: Double`
    * `transformation: FloatArray` (16 floats)

### `TextComponent`

| Field            | Type      | Description              |
|------------------|-----------|--------------------------|
| `text`           | `String`  | Text content             |
| `color`          | `String`  | Hex color code           |
| `isBold`         | `Boolean` | Bold formatting          |
| `isItalic`       | `Boolean` | Italic formatting        |
| `isUnderlined`   | `Boolean` | Underlined formatting    |
| `isStrikethough` | `Boolean` | Strikethrough formatting |
| `isObfuscated`   | `Boolean` | Obfuscated text          |
| `font`           | `String`  | Font identifier          |

---

## Running Tests

The project includes comprehensive 5 tests in `EntityParserTest`:

```kotlin
class EntityParserTest {
    lateinit var parser: EntityParser
    val rawCommand = """
        /summon block_display ~-0.5 ~-0.5 ~-0.5 {Passengers:[
          {id:\"minecraft:block_display\",block_state:{Name:\"minecraft:glass\",Properties:{}},transformation:[...]},
          {id:\"minecraft:block_display\",block_state:{Name:\"minecraft:glass\",Properties:{}},transformation:[...]},
          {id:\"minecraft:text_display\",text:[...],alignment:\"center\",line_width:220.5,...},
          {id:\"minecraft:block_display\",block_state:{Name:\"minecraft:frosted_ice\",Properties:{age:\"0\"}},transformation:[...]}
        ]}"""
    
    @BeforeEach
    fun setUp() {
        parser = EntityParser()
    }

    @Test
    fun `should parse four passengers`() {
        val result = parser.parseCommand(rawCommand)
        assertEquals(4, result.passengers.size)
    }

    // Additional tests validate types, names, properties, and matrix values...
}
```

Execute tests with Gradle:

```bash
gradlew test
```

---

## License

This project is licensed under the [Creative Commons Attribution-NonCommercial 4.0 International License](https://creativecommons.org/licenses/by-nc/4.0/).

Copyright © 2025
Sasha Zorov