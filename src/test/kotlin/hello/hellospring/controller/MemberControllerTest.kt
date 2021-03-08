package hello.hellospring.controller

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class MemberControllerTest(private val mockMvc: MockMvc) {

    @Test
    internal fun listTest() {
        mockMvc.get("/members")
            .andDo { log() }
            .andExpect { status { is2xxSuccessful() } }
            .andExpect { model { attributeExists("members") } }
            .andExpect { view { name("members/member-list") } }
            .andReturn()
    }
}