/*
 * Dependency-Check Plugin for SonarQube
 * Copyright (C) 2015-2019 SonarSecurityCommunity
 * philipp.dallig@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.dependencycheck.rule;

import org.mockito.InOrder;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.dependencycheck.base.DependencyCheckConstants;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class KnownCveRuleDefinitionTest {

    private KnownCveRuleDefinition rule = new KnownCveRuleDefinition();

    @Test
    public void define() throws Exception {
        final RulesDefinition.Context context = mock(RulesDefinition.Context.class);
        final RulesDefinition.NewRepository repo = mock(RulesDefinition.NewRepository.class);
        final RulesDefinition.NewRule rule = mock(RulesDefinition.NewRule.class, RETURNS_SMART_NULLS);

        when(repo.createRule(DependencyCheckConstants.RULE_KEY)).thenReturn(rule);
        when(context.createRepository(anyString(), anyString())).thenReturn(repo);

        this.rule.define(context);

        InOrder inOrder = inOrder(context, repo);

        inOrder.verify(context).createRepository("OWASP","neutral");
        inOrder.verify(repo).createRule(DependencyCheckConstants.RULE_KEY);

        inOrder.verify(repo).done();
        inOrder.verifyNoMoreInteractions();
    }

}