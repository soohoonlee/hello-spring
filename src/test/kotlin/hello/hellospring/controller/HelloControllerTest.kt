package hello.hellospring.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest
class HelloControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk)
            .andExpect(view().name("hello"))
    }
}
