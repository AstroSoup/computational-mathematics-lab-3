package ru.astrosoup.parser;

import ru.astrosoup.models.Node;

/**
 * Parser interface.
 */
public interface Parser {
    Node parse(String input);
}
