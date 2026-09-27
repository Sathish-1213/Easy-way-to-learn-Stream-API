import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Demonstrates commonly used Java 8 Stream API features.
public class Main {
    // The program starts here and runs each group of examples in order.
    public static void main(String[] args) {
        streamSources();
        intermediateOperations();
        terminalOperations();
        collectors();
        primitiveStreams();
    }

    // Streams can be created from collections, arrays, fixed values, or generators.
    private static void streamSources() {
        section("Creating streams");

        // A List is a collection; stream() creates a stream that reads its elements.
        List<String> names = Arrays.asList("Maya", "Arun", "Leah");
        // collect() is a terminal operation: it consumes the stream and builds a List.
        System.out.println("Collection: " + names.stream().collect(Collectors.toList()));

        // Stream.of() creates a stream directly from the supplied values.
        System.out.println("Stream.of: " + Stream.of("red", "green", "blue")
                .collect(Collectors.toList()));

        // Arrays.stream() creates a stream from an array.
        System.out.println("Arrays.stream: " + Arrays.stream(new String[] {"one", "two"})
                .collect(Collectors.toList()));

        // concat() joins two streams in order into one stream.
        System.out.println("Stream.concat: " + Stream.concat(Stream.of(1, 2), Stream.of(3, 4))
                .collect(Collectors.toList()));

        // iterate() repeatedly applies the function: start at 1, then add 1.
        // limit(5) keeps the first five generated values so the stream is finite.
        System.out.println("iterate: " + Stream.iterate(1, n -> n + 1)
                .limit(5).collect(Collectors.toList()));

        // generate() repeatedly calls its supplier; limit() stops after three values.
        System.out.println("generate: " + Stream.generate(() -> "Java")
                .limit(3).collect(Collectors.toList()));

        // Stream.empty() contains no elements, so its count is zero.
        System.out.println("empty stream count: " + Stream.empty().count());
    }

    // Intermediate operations transform/filter a stream and are lazy:
    // they run when a terminal operation (such as collect) consumes the stream.
    private static void intermediateOperations() {
        section("Intermediate operations");

        List<String> words = Arrays.asList("stream", "api", "java", "stream", "code");
        // filter keeps words with at least four characters.
        // map converts each remaining word to uppercase.
        // distinct removes duplicates, sorted orders the values, and collect makes a List.
        List<String> result = words.stream()
                .filter(word -> word.length() >= 4)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("filter, map, distinct, sorted: " + result);

        // distinct() keeps the first occurrence of each word.
        List<String> uniqueWords = words.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("distinct: " + uniqueWords);

        // skip(1) ignores the first item; limit(3) then takes at most three items.
        System.out.println("skip and limit: " + words.stream().skip(1).limit(3)
                .collect(Collectors.toList()));

        List<String> letters = Arrays.asList("a,b", "c,d");
        // flatMap() turns each input string into a stream of pieces, then flattens
        // all those small streams into one stream of individual strings.
        System.out.println("flatMap: " + letters.stream()
                .flatMap(value -> Arrays.stream(value.split(",")))
                .collect(Collectors.toList()));

        System.out.print("peek: ");
        // peek() observes elements as they pass through the pipeline.
        // It is mainly useful for debugging; map() then doubles each number.
        List<Integer> doubled = Arrays.asList(1, 2, 3).stream()
                .peek(value -> System.out.print("seen " + value + "; "))
                .map(value -> value * 2)
                .collect(Collectors.toList());
        System.out.println("result " + doubled);

        // mapToInt() converts each word to its length in a primitive IntStream.
        // boxed() converts primitive int values back to Integer objects for collecting.
        System.out.println("mapToInt: " + words.stream()
                .mapToInt(String::length)
                .boxed()
                .collect(Collectors.toList()));
    }

    // Terminal operations consume a stream and produce a result or a side effect.
    private static void terminalOperations() {
        section("Terminal operations");

        List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4);
        // forEach() performs an action for every element; here it prints each number.
        System.out.print("forEach: ");
        numbers.stream().forEach(value -> System.out.print(value + " "));
        System.out.println();

        // count() returns the number of elements in the stream.
        System.out.println("count: " + numbers.stream().count());

        // min() and max() use the comparator to find the smallest and largest values.
        // get() reads the value from Optional; these example streams are non-empty.
        System.out.println("min: " + numbers.stream().min(Integer::compareTo).get());
        System.out.println("max: " + numbers.stream().max(Integer::compareTo).get());

        // reduce() combines all values into one; zero is the starting value
        // and Integer.sum adds each next value to the running total.
        System.out.println("sum with reduce: " + numbers.stream().reduce(0, Integer::sum));

        // Match operations test a predicate and return true or false.
        System.out.println("anyMatch (> 8): " + numbers.stream().anyMatch(value -> value > 8));
        System.out.println("allMatch (> 0): " + numbers.stream().allMatch(value -> value > 0));
        System.out.println("noneMatch (< 0): " + numbers.stream().noneMatch(value -> value < 0));

        // findFirst() returns the first element; findAny() returns an available element.
        // Both return Optional because a stream could be empty.
        System.out.println("findFirst: " + numbers.stream().findFirst().get());
        System.out.println("findAny: " + numbers.stream().findAny().get());

        // This filter matches nothing, so findFirst() returns an empty Optional.
        Optional<Integer> missing = numbers.stream().filter(value -> value > 100).findFirst();
        // orElse(-1) supplies -1 when the Optional has no value.
        System.out.println("Optional fallback: " + missing.orElse(-1));
    }

    // Collectors define common ways to gather stream elements into result containers.
    private static void collectors() {
        section("Collectors");

        List<String> names = Arrays.asList("Asha", "Ben", "Amir", "Bea", "Alex");
        // toList() gathers all stream values into a List.
        System.out.println("toList: " + names.stream().collect(Collectors.toList()));

        // joining() combines strings into one string, placing ", " between values.
        System.out.println("joining: " + names.stream().collect(Collectors.joining(", ")));

        // groupingBy() creates a Map whose keys are each name's first letter
        // and whose values are lists of names beginning with that letter.
        System.out.println("groupingBy first letter: " + names.stream()
                .collect(Collectors.groupingBy(name -> name.substring(0, 1))));

        // partitioningBy() divides values into two groups: predicate true and false.
        System.out.println("partitioningBy length > 3: " + names.stream()
                .collect(Collectors.partitioningBy(name -> name.length() > 3)));

        // groupingBy() groups by first letter; counting() counts items in each group.
        System.out.println("counting by first letter: " + names.stream()
                .collect(Collectors.groupingBy(name -> name.substring(0, 1),
                        Collectors.counting())));
    }

    // Primitive streams avoid boxing numbers into wrapper objects and offer numeric helpers.
    private static void primitiveStreams() {
        section("Primitive streams");

        // rangeClosed(1, 5) creates the int values 1 through 5, including both endpoints.
        // sum() adds all values and consumes the IntStream.
        IntStream values = IntStream.rangeClosed(1, 5);
        System.out.println("IntStream rangeClosed sum: " + values.sum());

        // mapToDouble() converts the Double values to a primitive DoubleStream.
        // summaryStatistics() calculates count, sum, minimum, maximum, and average together.
        DoubleSummaryStatistics stats = Arrays.asList(2.5, 4.0, 7.5).stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();
        System.out.println("summaryStatistics: count=" + stats.getCount()
                + ", sum=" + stats.getSum()
                + ", min=" + stats.getMin()
                + ", max=" + stats.getMax()
                + ", average=" + stats.getAverage());

        // boxed() converts an IntStream into a Stream<Integer>, which can be collected as a List.
        System.out.println("boxed: " + IntStream.rangeClosed(1, 3).boxed()
                .collect(Collectors.toList()));
    }

    // Prints a heading to make each group of console output easy to distinguish.
    private static void section(String title) {
        System.out.println("\n== " + title + " ==");
    }
}
