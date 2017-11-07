package ru.spbau.annikura.collections;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.function.Function1;
import ru.spbau.annikura.function.Function2;
import ru.spbau.annikura.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Collections {
    public static <
            T, C extends Iterable<? extends T>, U,
            F extends Function1<? super T, ? extends U>>
    List<U> map(@NotNull final C collection, @NotNull final F func) {
        ArrayList<U> modifiedCollection = new ArrayList<>();
        for (T element : collection) {
            modifiedCollection.add(func.apply(element));
        }
        return modifiedCollection;
    }

    public static <T, C extends Iterable<? extends T>,
            P extends Predicate<? super T>>
    List<T> filter(@NotNull final C collection, @NotNull final P pred) {
        ArrayList<T> filteredElements = new ArrayList<>();
        for (T element : collection)
            if (pred.apply(element))
                filteredElements.add(element);
        return filteredElements;
    }

    public static <T, C extends Iterable<? extends T>,
            P extends Predicate<? super T>>
    List<T> takeWhile(@NotNull final C collection, @NotNull final P pred) {
        ArrayList<T> filteredElements = new ArrayList<>();
        for (T element : collection)
            if (pred.apply(element))
                filteredElements.add(element);
            else
                break;
        return filteredElements;
    }

    public static <T, C extends Iterable<? extends T>,
            P extends Predicate<? super T>>
    List<T> takeUntil(@NotNull final C collection, @NotNull final P pred) {
        return takeWhile(collection, pred.not());
    }

    private static <
            T, U, I extends Iterator<? extends T>,
            F extends Function2<? super T, ? super U, ? extends U>>
    U recursiveFoldr(@NotNull final I iterator, @NotNull final F func, final U initValue) {
        if (!iterator.hasNext())
            return initValue;
        else
            return func.apply(iterator.next(), recursiveFoldr(iterator, func, initValue));
    }

    public static <
            T, U, C extends Collection<? extends T>,
            F extends Function2<? super T, ? super U, ? extends U>>
    U foldr(@NotNull final C collection, @NotNull final F func, final U initValue) {
        return recursiveFoldr(collection.iterator(), func, initValue);
    }


    private static <
            T, U, I extends Iterator<? extends T>,
            F extends Function2<? super U, ? super T, ? extends U>>
    U recursiveFoldl(@NotNull final I iterator, @NotNull final F func, final U initValue) {
        if (!iterator.hasNext())
            return initValue;
        else
            return func.apply(recursiveFoldl(iterator, func, initValue), iterator.next());
    }

    public static <
            T, U, C extends Collection<? extends T>,
            F extends Function2<? super U, ? super T, ? extends U>>
    U foldl(@NotNull final C collection, @NotNull final F  func, final U initValue) {
        return recursiveFoldl(collection.iterator(), func, initValue);
    }
}
