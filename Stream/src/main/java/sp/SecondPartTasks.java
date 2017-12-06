package sp;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.StrictMath.sqrt;

public final class SecondPartTasks {

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    @NotNull
    public static List<String> findQuotes(final @NotNull List<String> paths, CharSequence sequence) {
        return paths.stream().filter(str -> str.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        return IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                double x = random.nextDouble() - 0.5;
                double y = random.nextDouble() - 0.5;
                if (sqrt(x * x + y * y) < 0.5) {
                    return 1;
                }
                return 0;
            }
            Random random = new Random();
        }).limit(100000).average().getAsDouble();
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    @NotNull
    public static String findPrinter(final @NotNull Map<String, List<String>> compositions) {
        if (compositions.size() == 0) {
            throw new IllegalArgumentException("Given map shouldn't be empty");
        }
        return compositions.entrySet().stream()
                .sorted(Comparator.comparing(composition -> -composition.getValue().stream()
                        .mapToInt(String::length)
                        .sum()
                ))
                .findFirst()
                .get()
                .getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    @NotNull
    public static Map<String, Integer> calculateGlobalOrder(final @NotNull List<Map<String, Integer>> orders) {
        return orders.stream()
                .flatMap(el -> el.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)));
    }
}
