package hello.hellospring.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HelloController {

    @GetMapping("hello")
    fun hello(model: Model): String {
        model["data"] = "hello!!"
        return "hello"
    }
    
    @GetMapping("hello-mvc")
    fun helloMvc(@RequestParam(value = "name") name: String, model: Model): String {
        model["name"] = name
        return "hello-template"
    }
}