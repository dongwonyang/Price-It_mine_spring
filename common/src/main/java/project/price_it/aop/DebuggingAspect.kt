package project.price_it.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class DebuggingAspect {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Pointcut("execution(* project.price_it.service.*(..))")
    private fun cut() {}

    @Before("cut()")
    fun loggingArgs(joinPoint: JoinPoint) {
        val args = joinPoint.args
        val className = joinPoint.target.javaClass.simpleName
        val methodName = joinPoint.signature.name

        args.forEach { arg ->
            log.info("{}#{}의 입력값 => {}", className, methodName, arg)
        }
    }

    @AfterReturning(pointcut = "cut()", returning = "returnObj")
    fun loggingReturnValue(joinPoint: JoinPoint, returnObj: Any?) {
        val className = joinPoint.target.javaClass.simpleName
        val methodName = joinPoint.signature.name

        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj)
    }
}
