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

package org.springframework.ws.soap.stroap;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;

/**
 * @author Arjen Poutsma
 */
public class StroapMessageFactory implements SoapMessageFactory {

	private final XMLInputFactory inputFactory = createXmlInputFactory();

	private final XMLOutputFactory outputFactory = createXmlOutputFactory();

	private final XMLEventFactory eventFactory = createXmlEventFactory();

	private final DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();

	private boolean payloadCaching = true;

	public boolean isPayloadCaching() {
		return payloadCaching;
	}

	public void setPayloadCaching(boolean payloadCaching) {
		this.payloadCaching = payloadCaching;
	}

	public SoapVersion getSoapVersion() {
		return SoapVersion.SOAP_11;
	}

	public void setSoapVersion(SoapVersion version) {
		if (version != SoapVersion.SOAP_11) {
			throw new UnsupportedOperationException();
		}
	}

	public StroapMessage createWebServiceMessage() {
		return new StroapMessage(this);
	}

	public StroapMessage createWebServiceMessage(InputStream inputStream) throws IOException {
		try {
			return StroapMessage.build(inputStream, this);
		}
		catch (XMLStreamException ex) {
			throw new StroapMessageCreationException("Could not create message from InputStream: " + ex.getMessage(),
					ex);
		}
	}

	XMLInputFactory getInputFactory() {
		return inputFactory;
	}

	XMLOutputFactory getOutputFactory() {
		return outputFactory;
	}

	XMLEventFactory getEventFactory() {
		return eventFactory;
	}

	DocumentBuilderFactory getDocumentBuilderFactory() {
		return documentBuilderFactory;
	}

	/**
	 * Create a {@code XMLInputFactory} that this message factory will use to create {@link
	 * javax.xml.stream.XMLEventReader} objects.
	 *
	 * <p>Can be overridden in subclasses, adding further initialization of the factory. The resulting factory is cached,
	 * so this method will only be called once.
	 *
	 * @return the created factory
	 */
	protected XMLInputFactory createXmlInputFactory() {
		return XMLInputFactory.newInstance();
	}

	/**
	 * Create a {@code XMLOutputFactory} that this message factory will use to create {@link
	 * javax.xml.stream.XMLEventWriter} objects.
	 *
	 * <p>Can be overridden in subclasses, adding further initialization of the factory. The resulting factory is cached,
	 * so this method will only be called once.
	 *
	 * @return the created factory
	 */
	protected XMLOutputFactory createXmlOutputFactory() {
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		outputFactory.setProperty("javax.xml.stream.isRepairingNamespaces", true);
		return outputFactory;
	}

	/**
	 * Create a {@code XMLEventFactory} that this message factory will use to create {@link
	 * javax.xml.stream.events.XMLEvent} objects.
	 *
	 * <p>Can be overridden in subclasses, adding further initialization of the factory. The resulting factory is cached,
	 * so this method will only be called once.
	 *
	 * @return the created factory
	 */
	protected XMLEventFactory createXmlEventFactory() {
		return XMLEventFactory.newFactory();
	}

	/**
	 * Create a {@code DocumentBuilderFactory} that this message factory will use to create {@link org.w3c.dom.Document} objects.
	 *
	 * <p>Can be overridden in subclasses, adding further initialization of the factory. The resulting factory is cached,
	 * so this method will only be called once.
	 *
	 * @return the created factory
	 */
	protected DocumentBuilderFactory createDocumentBuilderFactory() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		return documentBuilderFactory;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("StroapMessageFactory[");
		if (getSoapVersion() == SoapVersion.SOAP_11) {
			builder.append("SOAP 1.1");
		}
		else if (getSoapVersion() == SoapVersion.SOAP_12) {
			builder.append("SOAP 1.2");
		}
		builder.append(',');
		if (payloadCaching) {
			builder.append("PayloadCaching enabled");
		}
		else {
			builder.append("PayloadCaching disabled");
		}
		builder.append(']');
		return builder.toString();
	}


}
