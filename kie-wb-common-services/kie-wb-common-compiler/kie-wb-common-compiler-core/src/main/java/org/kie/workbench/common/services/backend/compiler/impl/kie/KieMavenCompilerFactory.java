/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.services.backend.compiler.impl.kie;

import org.kie.workbench.common.services.backend.compiler.AFCompiler;
import org.kie.workbench.common.services.backend.compiler.CompilationResponse;
import org.kie.workbench.common.services.backend.compiler.configuration.KieDecorator;
import org.kie.workbench.common.services.backend.compiler.impl.BaseMavenCompiler;
import org.kie.workbench.common.services.backend.compiler.impl.decorators.ClasspathDepsAfterDecorator;
import org.kie.workbench.common.services.backend.compiler.impl.decorators.JGITCompilerBeforeDecorator;
import org.kie.workbench.common.services.backend.compiler.impl.decorators.KieAfterDecorator;
import org.kie.workbench.common.services.backend.compiler.impl.decorators.OutputLogAfterDecorator;

/***
 * Factory to create compilers with correct order of decorators to build Kie Projects
 * working with the kie takari plugin
 */
public class KieMavenCompilerFactory {

    private KieMavenCompilerFactory() {
    }

    /**
     * Provides a Maven compiler decorated with a Decorator Behaviour
     */
    public static <T extends CompilationResponse> AFCompiler<T> getCompiler(KieDecorator decorator) {
        return createAndAddNewCompiler(decorator);
    }

    private static <T extends CompilationResponse> AFCompiler<T> createAndAddNewCompiler(KieDecorator decorator) {

        AFCompiler compiler;
        switch (decorator) {
            case NONE:
                compiler = new BaseMavenCompiler();
                break;

            case CLASSPATH_DEPS_AFTER_DECORATOR:
                compiler = new ClasspathDepsAfterDecorator(new BaseMavenCompiler());
                break;

            case KIE_AFTER:
                compiler = new KieAfterDecorator(new BaseMavenCompiler());
                break;

            case KIE_AND_CLASSPATH_AFTER_DEPS:
                compiler = new KieAfterDecorator(new ClasspathDepsAfterDecorator(new BaseMavenCompiler()));
                break;

            case KIE_LOG_AND_CLASSPATH_DEPS_AFTER:
                compiler = new KieAfterDecorator(new OutputLogAfterDecorator(new ClasspathDepsAfterDecorator(new BaseMavenCompiler())));
                break;

            case KIE_AND_LOG_AFTER:
                compiler = new KieAfterDecorator(new OutputLogAfterDecorator(new BaseMavenCompiler()));
                break;

            case JGIT_BEFORE:
                compiler = new JGITCompilerBeforeDecorator(new BaseMavenCompiler());
                break;

            case JGIT_BEFORE_AND_LOG_AFTER:
                compiler = new JGITCompilerBeforeDecorator(new OutputLogAfterDecorator(new BaseMavenCompiler()));
                break;

            case JGIT_BEFORE_AND_KIE_AFTER:
                compiler = new JGITCompilerBeforeDecorator(new KieAfterDecorator(new BaseMavenCompiler()));
                break;

            case LOG_OUTPUT_AFTER:
                compiler = new OutputLogAfterDecorator(new BaseMavenCompiler());
                break;

            case JGIT_BEFORE_AND_KIE_AND_LOG_AFTER:
                compiler = new JGITCompilerBeforeDecorator(
                        new KieAfterDecorator(
                                new OutputLogAfterDecorator(
                                        new BaseMavenCompiler())));
                break;

            case JGIT_BEFORE_AND_KIE_AND_LOG_AND_CLASSPATH_AFTER:
                compiler = new JGITCompilerBeforeDecorator(
                        new KieAfterDecorator(
                                new OutputLogAfterDecorator(
                                        new ClasspathDepsAfterDecorator(
                                                new BaseMavenCompiler()))));
                break;

            default:
                compiler = new BaseMavenCompiler();
        }
        return compiler;
    }
}