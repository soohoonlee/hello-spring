package hello.hellospring.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

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
    
    @GetMapping("hello-string")
    @ResponseBody
    fun helloString(@RequestParam(value = "name") name: String): String {
        return "hello $name"
    }

    @GetMapping("hello-api")
    @ResponseBody
    fun helloApi(@RequestParam(value = "name") name: String): Hello {
        return Hello(name)
    }

    data class Hello(var name: String)
}