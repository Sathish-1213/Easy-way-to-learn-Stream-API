# Easy Way to Learn Java 8 Stream API

This project is a beginner-friendly, runnable introduction to the Java 8 Stream API. The examples in [`Main.java`](Main.java) demonstrate how to create streams, process data, and collect results.

## Requirements

- Java Development Kit (JDK) 8 or later
- A terminal or command prompt

Check that Java is installed:

```text
java -version
javac -version
```

## Compile and run

Open a terminal in the folder containing `Main.java`, then run:

```text
javac Main.java
java Main
```

The program prints each group of examples to the console.

## Topics covered

- **Creating streams:** `Collection.stream()`, `Stream.of()`, `Arrays.stream()`, `Stream.concat()`, `Stream.iterate()`, `Stream.generate()`, and `Stream.empty()`
- **Intermediate operations:** `filter()`, `map()`, `distinct()`, `sorted()`, `skip()`, `limit()`, `flatMap()`, `peek()`, and `mapToInt()`
- **Terminal operations:** `forEach()`, `count()`, `min()`, `max()`, `reduce()`, `anyMatch()`, `allMatch()`, `noneMatch()`, `findFirst()`, and `findAny()`
- **Optional values:** handling a possibly missing result with `orElse()`
- **Collectors:** `toList()`, `joining()`, `groupingBy()`, `partitioningBy()`, and `counting()`
- **Primitive streams:** `IntStream`, `DoubleStream` conversions, `rangeClosed()`, `sum()`, `summaryStatistics()`, and `boxed()`

## What is a stream?

A stream is a sequence of values that lets you describe data-processing steps. A typical stream pipeline has three parts:

1. **Source** — where the values come from, such as a collection or array.
2. **Intermediate operations** — steps that transform or filter values. These are lazy and run when a terminal operation consumes the stream.
3. **Terminal operation** — consumes the stream and produces a result or side effect, such as a list, a count, or printed output.

For example:

```java
List<String> longNames = names.stream()
        .filter(name -> name.length() > 4)
        .map(String::toUpperCase)
        .collect(Collectors.toList());
```

This creates a stream from `names`, keeps names longer than four characters, converts them to uppercase, and gathers the results into a list.

## Notes

- A stream is intended to be consumed once. Create a new stream from the source collection when you need another pipeline.
- `peek()` is useful for observing values while debugging; use `forEach()` when you want a terminal action.
- `Stream.iterate()` and `Stream.generate()` can produce unbounded streams. Use an operation such as `limit()` when you only need a finite number of values.
- The examples use Java 8 APIs and syntax.
