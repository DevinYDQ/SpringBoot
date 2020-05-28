package com.ydq.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.ydq.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void beforeLog(JoinPoint point) {
        logger.info("@Before==================");
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        logger.info("[URL] : {}", request.getRequestURL());
        logger.info("[IP] : {}", request.getRemoteAddr());
        logger.info("[request method] : {}", request.getMethod());
        logger.info("[CLASS]: {}, [METHOD] : {}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());

        Map<String, String[]> paramMap = request.getParameterMap();
        logger.info("[params] : {}, [body] : {}", JSON.toJSONString(paramMap), JSON.toJSONString(point.getArgs()));


    }

    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        logger.info("@Around==================");
        Object result = point.proceed();
        logger.info("[returned result:] : {}", JSON.toJSONString(result));
        return result;
    }

    @AfterReturning("log()")
    public void afterReturning() {
        logger.info("@AfterReturning==================");

        logger.info("[costMS] : {}", (System.currentTimeMillis() - startTime.get()));
    }

    @AfterThrowing("log()")
    public void throwss(JoinPoint point) {
        logger.info("Exception occuringg...........");
    }

    @After("log()")
    public void after(JoinPoint point) {
        logger.info("@After==================");
    }
}
