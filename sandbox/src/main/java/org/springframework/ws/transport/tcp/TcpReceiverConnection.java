/*
 * Copyright 2005-2010 the original author or authors.
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

package org.springframework.ws.transport.tcp;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.util.Assert;
import org.springframework.ws.transport.AbstractReceiverConnection;
import org.springframework.ws.transport.tcp.support.TcpTransportUtils;

/** @author Arjen Poutsma */
public class TcpReceiverConnection extends AbstractReceiverConnection {

	private final Socket socket;

	protected TcpReceiverConnection(Socket socket) {
		Assert.notNull(socket, "socket must not be null");
		this.socket = socket;
	}

	public URI getUri() throws URISyntaxException {
		return TcpTransportUtils.toUri(socket);
	}

	public boolean hasError() throws IOException {
		return false;
	}

	public String getErrorMessage() throws IOException {
		return null;
	}

	public void onClose() throws IOException {
		socket.close();
	}

	protected Iterator getRequestHeaderNames() throws IOException {
		return Collections.EMPTY_LIST.iterator();
	}

	protected Iterator getRequestHeaders(String name) throws IOException {
		return Collections.EMPTY_LIST.iterator();
	}

	protected InputStream getRequestInputStream() throws IOException {
		return new FilterInputStream(socket.getInputStream()) {

			@Override
			public void close() throws IOException {
				// don't close the socket
				socket.shutdownInput();
			}
		};
	}

	protected void addResponseHeader(String name, String value) throws IOException {
	}

	protected OutputStream getResponseOutputStream() throws IOException {
		return new FilterOutputStream(socket.getOutputStream()) {

			@Override
			public void close() throws IOException {
				// don't close the socket
				socket.shutdownOutput();
			}
		};
	}

	protected void sendResponse(boolean sentFault) throws IOException {
	}

}
