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

package org.springframework.xml.stream;

import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.util.Assert;

/**
 * Implementation of {@link XMLEventReader} that combines multiple other {@code XMLEventReader}s.
 *
 * @author Arjen Poutsma
 */
public class CompositeXMLEventReader extends AbstractXMLEventReader {

	private final XMLEventReader[] eventReaders;

	private int cursor = 0;

	public CompositeXMLEventReader(XMLEventReader eventReader) {
		Assert.notNull(eventReader, "'eventReader' must not be null");
		this.eventReaders = new XMLEventReader[]{eventReader};
	}

	public CompositeXMLEventReader(XMLEventReader... eventReaders) {
		Assert.notNull(eventReaders, "'eventReaders' must not be null");
		this.eventReaders = eventReaders;
	}

	public CompositeXMLEventReader(List<XMLEventReader> eventReaders) {
		Assert.notNull(eventReaders, "'eventReaders' must not be null");
		this.eventReaders = eventReaders.toArray(new XMLEventReader[eventReaders.size()]);
	}

	public boolean hasNext() {
		while (cursor < eventReaders.length) {
			if (!atLastEventReader()) {
				if (!currentEventReader().hasNext()) {
					cursor++;
					continue;
				}
			}
			return currentEventReader().hasNext();
		}
		return false;
	}

	public XMLEvent nextEvent() throws XMLStreamException {
		XMLEvent event = null;
		while (cursor < eventReaders.length) {
			event = currentEventReader().nextEvent();
			if (!atLastEventReader() && event.isEndDocument()) {
				cursor++;
			}
			else {
				break;
			}
		}
		return event;
	}

	public XMLEvent peek() throws XMLStreamException {
		XMLEvent event = null;
		while (cursor < eventReaders.length) {
			event = currentEventReader().peek();
			if (!atLastEventReader() && (event == null || event.isEndDocument())) {
				cursor++;
			}
			else {
				break;
			}
		}
		return event;
	}

	private XMLEventReader currentEventReader() {
		return eventReaders[cursor];
	}

	private boolean atLastEventReader() {
		return cursor == eventReaders.length - 1;
	}

}
