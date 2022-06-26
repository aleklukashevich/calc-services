package utils;

import io.qameta.allure.model.StepResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.util.AspectUtils.getParameters;

@Aspect
public final class DneOnlineAddAspect {
    @Pointcut("execution(* *..CalculatorSoap.add(..))")
    public void anyMethod() {
    }

    @Before("anyMethod()")
    public void addStep(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        List<StepResult> steps = new ArrayList<>();
        steps.add(new StepResult().setName(methodSignature.getName())
                .setParameters(new ArrayList<>(getParameters(methodSignature, joinPoint.getArgs()))));
        getLifecycle().updateTestCase(testResult -> testResult.setSteps(steps));
    }

}
