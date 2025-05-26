package dev.zornov.bdengine

import dev.zornov.bdengine.model.BlockDisplay
import dev.zornov.bdengine.model.TextDisplay
import dev.zornov.bdengine.service.EntityParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EntityParserTest {

    lateinit var parser: EntityParser
    val rawCommand = """
        /summon block_display ~-0.5 ~-0.5 ~-0.5 {Passengers:[
          {id:"minecraft:block_display",block_state:{Name:"minecraft:glass",Properties:{}},
           transformation:[0.5625f,0f,0f,1.21875f,0f,0.6875f,0f,0.004375f,0f,0f,0.625f,1.1875f,0f,0f,0f,1f]},
          {id:"minecraft:block_display",block_state:{Name:"minecraft:glass",Properties:{}},
           transformation:[0.125f,0f,0f,1.4375f,0f,0.3125f,0f,0.6825f,0f,0f,0.1875f,1.3925f,0f,0f,0f,1f]},
          {id:"minecraft:text_display",
           text:[{"text":"Water","color":"#ffffff","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false,"font":"minecraft:uniform"}],
           text_opacity:255,background:-13172481,alignment:"center",line_width:220.5,default_background:false,
           transformation:[1f,0f,0f,1.488125f,0f,1f,0f,0.20125f,0f,0f,93.4375f,1.796875f,0f,0f,0f,1f]},
          {id:"minecraft:block_display",block_state:{Name:"minecraft:frosted_ice",Properties:{age:"0"}},
           transformation:[0.5625f,0f,0f,1.21125f,0f,0.5f,0f,0.035625f,0f,0f,0.5625f,1.21875f,0f,0f,0f,1f]}
        ]}
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        parser = EntityParser()
    }

    @Test
    fun `should parse four passengers`() {
        val bd = parser.parseCommand(rawCommand)
        assertEquals(4, bd.passengers.size, "Expected 4 passengers")
    }

    @Test
    fun `first passenger is BlockDisplay with glass`() {
        val bd = parser.parseCommand(rawCommand)
        val first = bd.passengers[0]
        assertTrue(first is BlockDisplay, "First passenger should be BlockDisplay")
        first as BlockDisplay
        assertEquals("minecraft:glass", first.blockState.name, "Block name mismatch")

        assertEquals(0.5625f, first.transformation[0], "Transformation[0] mismatch")
        assertEquals(1.21875f, first.transformation[3], "Transformation[3] mismatch")
    }

    @Test
    fun `second passenger is BlockDisplay with glass`() {
        val bd = parser.parseCommand(rawCommand)
        val second = bd.passengers[1]
        assertTrue(second is BlockDisplay, "Second passenger should be BlockDisplay")
        second as BlockDisplay
        assertEquals("minecraft:glass", second.blockState.name, "Block name mismatch")
        assertEquals(0.125f, second.transformation[0], "Transformation[0] mismatch")
        assertEquals(1.3925f, second.transformation[11], "Transformation[11] mismatch")
    }

    @Test
    fun `third passenger is TextDisplay with Water`() {
        val bd = parser.parseCommand(rawCommand)
        val third = bd.passengers[2]
        assertTrue(third is TextDisplay, "Third passenger should be TextDisplay")
        third as TextDisplay

        assertEquals(1, third.components.size, "Expected one text component")
        val comp = third.components.first()
        assertEquals("Water", comp.text, "Text content mismatch")
        assertEquals("#ffffff", comp.color, "Text color mismatch")
        assertFalse(comp.isBold, "Expected bold=false")
        assertEquals(220.5, third.lineWidth, "Line width mismatch")
        assertEquals("center", third.alignment, "Alignment mismatch")
    }

    @Test
    fun `fourth passenger is BlockDisplay with frosted_ice and age property`() {
        val bd = parser.parseCommand(rawCommand)
        val fourth = bd.passengers[3]
        assertTrue(fourth is BlockDisplay, "Fourth passenger should be BlockDisplay")
        fourth as BlockDisplay
        assertEquals("minecraft:frosted_ice", fourth.blockState.name, "Block name mismatch")
        assertEquals(mapOf("age" to "0"), fourth.blockState.properties, "Block properties mismatch")
    }
}
