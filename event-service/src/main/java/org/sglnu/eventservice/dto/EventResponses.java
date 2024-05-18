package org.sglnu.eventservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class EventResponses implements Iterable<EventResponse>{

    List<EventResponse> eventResponses;

    @NotNull
    @Override
    public Iterator<EventResponse> iterator() {
        return eventResponses.iterator();
    }

    @Override
    public void forEach(Consumer<? super EventResponse> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<EventResponse> spliterator() {
        return Iterable.super.spliterator();
    }

    public void add(EventResponse eventResponse) {
        eventResponses.add(eventResponse);
    }

}
