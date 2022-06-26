/*
 *  Copyright 2019 Qameta Software OÜ
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package utils;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.util.AspectUtils.getParameters;

@Aspect
public final class AllureMethodNameAspect {

    @Pointcut("@annotation(org.junit.jupiter.api.DisplayName)")
    public void withDisplayName() {
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
    }

    @Before("anyMethod() && withDisplayName()")
    public void changeName(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final DisplayName displayName = methodSignature.getMethod().getAnnotation(DisplayName.class);

        final List<Parameter> parameters = getParameters(methodSignature, joinPoint.getArgs());
        String methodName = displayName.value();
        Pattern pattern = Pattern.compile("\\{[0-9]\\}");
        Matcher matcher = pattern.matcher(methodName);
        while (matcher.find()){
            val index = Integer.parseInt(matcher.group().replaceAll("\\{|\\}", ""));
           methodName =  methodName.replaceAll("\\{"+index+"\\}", parameters.get(index).getValue());
        }

        String finalMethodName = methodName;
        getLifecycle().updateTestCase(testResult -> testResult.setName(finalMethodName));
    }

}