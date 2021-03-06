/*
 * Copyright 2008-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crm.domain.page;

/**
 * A page is a sublist of a list of objects. It allows gain information about the position of it in the containing
 * entire list.
 * 
 * @param <T>
 * @author Oliver Gierke
 */
public interface Page<T> extends Slice<T> {

	/**
	 * Returns the number of total pages.
	 * 
	 * @return the number of toral pages
	 */
	int getTotalPages();

	/**
	 * Returns the total amount of elements.
	 * 
	 * @return the total amount of elements
	 */
	long getTotalElements();

	/**
	 * Returns if there is a previous page.
	 * 
	 * @deprecated use {@link #hasPrevious()} instead.
	 * @return if there is a previous page
	 */
	@Deprecated
	boolean hasPreviousPage();

	/**
	 * Returns whether the current page is the first one.
	 * 
	 * @deprecated use {@link #isFirst()} instead.
	 * @return
	 */
	@Deprecated
	boolean isFirstPage();

	/**
	 * Returns if there is a next page.
	 * 
	 * @deprecated use {@link #hasNext()} instead.
	 * @return if there is a next page
	 */
	@Deprecated
	boolean hasNextPage();

	/**
	 * Returns whether the current page is the last one.
	 * 
	 * @deprecated use {@link #isLast()} instead.
	 * @return
	 */
	@Deprecated
	boolean isLastPage();
}
