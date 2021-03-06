/*
 * Copyright 2014 Daniel Bechler
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

package de.danielbechler.diff.mock;

import de.danielbechler.diff.introspection.ObjectDiffEqualsOnlyType;
import de.danielbechler.diff.introspection.ObjectDiffProperty;

/**
 * @author Daniel Bechler
 */
@ObjectDiffEqualsOnlyType
public class ObjectWithPropertyAnnotations extends ObjectWithHashCodeAndEquals
{
	private String ignored;
	private String categorized;

	public ObjectWithPropertyAnnotations(final String key)
	{
		super(key);
	}

	public ObjectWithPropertyAnnotations(final String key, final String value)
	{
		super(key, value);
	}

	@ObjectDiffProperty(excluded = true)
	public String getIgnored()
	{
		return ignored;
	}

	public void setIgnored(final String ignored)
	{
		this.ignored = ignored;
	}

	@ObjectDiffProperty(categories = {"foo"})
	public String getCategorized()
	{
		return categorized;
	}

	public void setCategorized(final String categorized)
	{
		this.categorized = categorized;
	}
}
