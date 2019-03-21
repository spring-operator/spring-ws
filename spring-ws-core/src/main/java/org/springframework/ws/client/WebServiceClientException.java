/*
 * Copyright 2005-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.client;

import org.springframework.ws.WebServiceException;

/**
 * Exception thrown whenever an error occurs on the client-side.
 *
 * @author Arjen Poutsma
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class WebServiceClientException extends WebServiceException {

	/**
	 * Create a new instance of the {@code WebServiceClientException} class.
	 *
	 * @param msg the detail message
	 */
	public WebServiceClientException(String msg) {
		super(msg);
	}

	/**
	 * Create a new instance of the {@code WebServiceClientException} class.
	 *
	 * @param msg the detail message
	 * @param ex  the root {@link Throwable exception}
	 */
	public WebServiceClientException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
