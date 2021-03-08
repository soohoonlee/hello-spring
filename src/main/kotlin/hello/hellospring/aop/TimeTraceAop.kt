package hello.hellospring.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception

@Aspect
@Component
class TimeTraceAop {
    private val log = LoggerFactory.getLogger(javaClass)

    @Before("execution(* hello.hellospring..*(..))")
    fun before(joinPoint: JoinPoint) {
        log.info("before")
    }

    @Around("execution(* hello.hellospring..*(..))")
    @Throws(Throwable::class)
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val timeMs = System.currentTimeMillis() - start
        log.info("${joinPoint.signature} executed in ${timeMs}mx")
        return result
    }

    @After("execution(* hello.hellospring..*(..))")
    fun after(joinPoint: JoinPoint) {
        log.info("after")
    }

    @AfterReturning(value = "execution(* hello.hellospring..*(..))", returning = "resource")
    fun afterReturning(resource: Any?) {
        log.info("after Returning $resource")
    }

    @AfterThrowing(value = "execution(* hello.hellospring..*(..))", throwing = "exception")
    fun afterThrowing(exception: Exception) {
        log.info("after Throwing", exception)
    }
}