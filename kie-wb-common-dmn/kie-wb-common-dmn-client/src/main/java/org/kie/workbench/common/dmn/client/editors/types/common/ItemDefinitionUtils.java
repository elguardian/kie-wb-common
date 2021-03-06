/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.client.editors.types.common;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.kie.workbench.common.dmn.api.definition.v1_1.ItemDefinition;
import org.kie.workbench.common.dmn.client.graph.DMNGraphUtils;

@Dependent
public class ItemDefinitionUtils {

    private final DMNGraphUtils dmnGraphUtils;

    @Inject
    public ItemDefinitionUtils(final DMNGraphUtils dmnGraphUtils) {
        this.dmnGraphUtils = dmnGraphUtils;
    }

    public Optional<ItemDefinition> findByName(final String name) {

        final List<ItemDefinition> getItemDefinition = dmnGraphUtils.getDefinitions().getItemDefinition();

        return getItemDefinition
                .stream()
                .filter(itemDefinition -> itemDefinition.getName().getValue().equals(name))
                .findFirst();
    }
}
