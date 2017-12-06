package sp;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    @NotNull
    public static List<String> allNames(final @NotNull Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    @NotNull
    public static List<String> allNamesSorted(final @NotNull Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    @NotNull
    public static List<String> allTracksSorted(final @NotNull Stream<Album> albums) {
        return albums.map(Album::getTracks)
                .flatMap(List::stream)
                .map(Track::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    @NotNull
    public static List<Album> sortedFavorites(final @NotNull Stream<Album> s) {
        return s.filter(album -> album.getTracks().stream().anyMatch(track -> track.getRating() > 95))
                .sorted(Comparator.comparing(Album::getName))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    @NotNull
    public static Map<Artist, List<Album>> groupByArtist(final @NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    @NotNull
    public static Map<Artist, List<String>> groupByArtistMapName(final @NotNull Stream<Album> albums) {
        return albums.collect(
                Collectors.groupingBy(Album::getArtist,
                        Collectors.mapping(Album::getName,
                                           Collectors.toList()))
        );
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(final @NotNull Stream<Album> albums) {
        return albums
                .sorted(Comparator.comparing(Album::getName))
                .reduce(0, new BiFunction<Integer, Album, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Album album) {
                        if (previousAlbum == null || !album.getName().equals(previousAlbum.getName())) {
                            previousAlbum = album;
                            return integer;
                        }
                        return integer + 1;
                    }

                    private Album previousAlbum = null;
                }, (integer, integer2) -> integer + integer2);
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    @NotNull
    public static Optional<Album> minMaxRating(final @NotNull Stream<Album> albums) {
        return albums.sorted(
                    Comparator.comparing(album ->
                            album.getTracks().stream()
                            .mapToInt(Track::getRating)
                            .max()
                            .orElse(0))
        ).findFirst();
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    @NotNull
    public static List<Album> sortByAverageRating(final @NotNull Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(
                album -> -album.getTracks().stream().mapToInt(Track::getRating).average().orElse(0)
        )).collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(final @NotNull IntStream stream, int modulo) {
        return stream.reduce(1, (left, right) -> left * right % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    @NotNull
    public static String joinTo(String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    @NotNull
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return s.filter(clazz::isInstance).map(clazz::cast);
    }
}
