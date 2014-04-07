/*
 * Copyright 2013 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.danielbechler.diff;

import de.danielbechler.util.Assert;
import de.danielbechler.util.Exceptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.danielbechler.util.Objects.isEqual;

/**
 * @author Daniel Bechler
 */
public class EqualsOnlyComparisonStrategy implements ComparisonStrategy
{
	private final String equalsValueProviderMethod;

	public EqualsOnlyComparisonStrategy()
	{
		this.equalsValueProviderMethod = null;
	}

	public EqualsOnlyComparisonStrategy(final String equalsValueProviderMethod)
	{
		Assert.hasText(equalsValueProviderMethod, "equalsValueProviderMethod");
		this.equalsValueProviderMethod = equalsValueProviderMethod;
	}

	private static Object access(final Object target, final String methodName)
	{
		if (target == null)
		{
			return null;
		}
		try
		{
			final Method method = target.getClass().getMethod(methodName);
			method.setAccessible(true);
			return method.invoke(target);
		}
		catch (final NoSuchMethodException e)
		{
			throw Exceptions.escalate(e);
		}
		catch (final InvocationTargetException e)
		{
			throw Exceptions.escalate(e);
		}
		catch (final IllegalAccessException e)
		{
			throw Exceptions.escalate(e);
		}
	}

	public void compare(final DiffNode node, final Instances instances)
	{
		if (hasEqual(instances))
		{
			node.setState(DiffNode.State.UNTOUCHED);
		}
		else
		{
			node.setState(DiffNode.State.CHANGED);
		}
	}

	private boolean hasEqual(final Instances instances)
	{
		if (equalsValueProviderMethod != null)
		{
			final Object working = access(instances.getWorking(), equalsValueProviderMethod);
			final Object base = access(instances.getBase(), equalsValueProviderMethod);
			return isEqual(working, base);
		}
		else
		{
			return isEqual(instances.getWorking(), instances.getBase());
		}
	}

	public boolean hasValueProviderMethod()
	{
		return equalsValueProviderMethod != null;
	}

	public String getEqualsValueProviderMethod()
	{
		return equalsValueProviderMethod;
	}
}
