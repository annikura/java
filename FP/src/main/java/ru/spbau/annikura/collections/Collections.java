package ru.spbau.annikura.collections;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.function.Function1;
import ru.spbau.annikura.function.Function2;
import ru.spbau.annikura.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of basic operations with iterable objects.
 */
public class Collections {
    /**
     * Applies given function to each element of the given collection. Returns list of the applications results.
     * @param collection iterable container to be modified.
     * @param func function to be applied to the container elements.
     * @param <ElementType> type of the elements stored in the container.
     * @param <NewType> type the elements will be converted to.
     * @param <IterableContainer> type of the given container.
     * @param <FunctorType> type of the given function.
     * @return a list of result elements.
     */
    public static <
            ElementType, NewType, IterableContainer extends Iterable<? extends ElementType>,
            FunctorType extends Function1<? super ElementType, ? extends NewType>>
    List<NewType> map(@NotNull final IterableContainer collection, @NotNull final FunctorType func) {
        ArrayList<NewType> modifiedCollection = new ArrayList<>();
        for (ElementType element : collection) {
            modifiedCollection.add(func.apply(element));
        }
        return modifiedCollection;
    }

    /**
     * Filters elements in the container.
     * @param collection iterable container storing elements.
     * @param predicate filter predicate.
     * @param <IterableContainer> type of the given container.
     * @param <PredicateType> type of the given predicate.
     * @return a list of elements for which predicate returned true.
     */
    public static <
            ElementType, IterableContainer extends Iterable<? extends ElementType>,
            PredicateType extends Predicate<? super ElementType>>
    List<ElementType> filter(@NotNull final IterableContainer collection, @NotNull final PredicateType predicate) {
        ArrayList<ElementType> filteredElements = new ArrayList<>();
        for (ElementType element : collection)
            if (predicate.apply(element))
                filteredElements.add(element);
        return filteredElements;
    }

    /**
     * Takes elements from the container until meets an element for which predicate returns false.
     * @param collection iterable container storing elements.
     * @param predicate predicate function
     * @param <ElementType> type of the container elements
     * @param <IterableContainer> type of the container
     * @param <PredicateType> type of the predicate function
     * @return list with the elements from the container taken until
     * predicate meets an element for which it returns false.
     */
    public static <
            ElementType, IterableContainer extends Iterable<? extends ElementType>,
            PredicateType extends Predicate<? super ElementType>>
    List<ElementType> takeWhile(@NotNull final IterableContainer collection,
                                @NotNull final PredicateType predicate) {
        ArrayList<ElementType> filteredElements = new ArrayList<>();
        for (ElementType element : collection)
            if (predicate.apply(element))
                filteredElements.add(element);
            else
                break;
        return filteredElements;
    }

    /**
     * Takes elements from the container until meets an element for which predicate returns true.
     * @param collection iterable container storing elements.
     * @param predicate predicate function
     * @param <ElementType> type of the container elements
     * @param <IterableContainer> type of the container
     * @param <PredicateType> type of the predicate function
     * @return list with the elements from the container taken until
     * predicate meets an element for which it returns true.
     */
    public static <
            ElementType, IterableContainer extends Iterable<? extends ElementType>,
            PredicateType extends Predicate<? super ElementType>>
    List<ElementType> takeUntil(@NotNull final IterableContainer collection,
                                @NotNull final PredicateType predicate) {
        return takeWhile(collection, predicate.not());
    }

    /**
     * Makes a right fold of the collection.
     * @param collection a collection to be folded.
     * @param func folding two-argument function.
     * @param initValue an initial value which will be returned if collection is empty. If it's not,
     * it will be given as a right parameter to the first function application.
     * @param <ElementType> type of the elements of the container.
     * @param <NewType> type of initial and returned values.
     * @param <CollectionType> type of the collection.
     * @param <FunctionType> type of the folding function.
     * @return a result of folding.
     */
    public static <
            ElementType, NewType, CollectionType extends Collection<? extends ElementType>,
            FunctionType extends Function2<? super ElementType, ? super NewType, ? extends NewType>>
    NewType foldR(@NotNull final CollectionType collection,
                  @NotNull final FunctionType func,
                  /*Nullability is not known, function-defined*/ final NewType initValue) {
        return foldRImplementation(collection.iterator(), func, initValue);
    }

    /**
     * Makes a left fold of the collection.
     * @param collection a collection to be folded.
     * @param func folding two-argument function.
     * @param initValue an initial value which will be returned if collection is empty. If it's not,
     * it will be given as a left parameter to the first function application.
     * @param <ElementType> type of the elements of the container.
     * @param <NewType> type of initial and returned values.
     * @param <CollectionType> type of the collection.
     * @param <FunctionType> type of the folding function.
     * @return a result of folding.
     */
    public static <
            ElementType, NewType, CollectionType extends Collection<? extends ElementType>,
            FunctionType extends Function2<? super NewType, ? super ElementType, ? extends NewType>>
    NewType foldL(@NotNull final CollectionType collection,
                  @NotNull final FunctionType func,
                  /*Nullability is not known, function-defined*/ final NewType initValue) {
        return foldLImplementation(collection.iterator(), func, initValue);
    }


    private static <
            ElementType, NewType, IteratorType extends Iterator<? extends ElementType>,
            FunctionType extends Function2<? super ElementType, ? super NewType, ? extends NewType>>
    NewType foldRImplementation(@NotNull final IteratorType iterator,
                                @NotNull final FunctionType func,
                           /*Nullability is not known, function-defined*/ final NewType initValue) {
        if (!iterator.hasNext())
            return initValue;
        else
            return func.apply(iterator.next(), foldRImplementation(iterator, func, initValue));
    }


    private static <
            ElementType, NewType, IteratorType extends Iterator<? extends ElementType>,
            FunctionType extends Function2<? super NewType, ? super ElementType, ? extends NewType>>
    NewType foldLImplementation(@NotNull final IteratorType iterator,
                                @NotNull final FunctionType func,
                           /*Nullability is not known, function-defined*/ final NewType initValue) {
        if (!iterator.hasNext())
            return initValue;
        else
            return func.apply(foldLImplementation(iterator, func, initValue), iterator.next());
    }
}
