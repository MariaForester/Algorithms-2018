package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        //TODO
        throw new NotImplementedError();
    }


    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            // TODO
            throw new NotImplementedError();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    // Трудоемкость T = O(n * log(n))
    // Ресурсоемкость R = O(n)
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> sortedSet = new TreeSet<>();
        headSetRecursive(toElement, sortedSet, root);
        return sortedSet;
    }

    private void headSetRecursive(T toElement, SortedSet<T> sortedSet, Node<T> currentNode) {
        int edgeComparison = currentNode.value.compareTo(toElement);
        if (edgeComparison == -1) {
            sortedSet.add(currentNode.value);
            if (currentNode.right != null) {
                headSetRecursive(toElement, sortedSet, currentNode.right);
            }
            if (currentNode.left != null) {
                newNodeAddition(sortedSet, currentNode.left);
            }
        } else if (edgeComparison == 0) {
            if (currentNode.left != null) {
                newNodeAddition(sortedSet, currentNode.left);
            }
        } else {
            if (currentNode.left != null) {
                headSetRecursive(toElement, sortedSet, currentNode.left);
            }
        }
    }

    private void newNodeAddition(SortedSet<T> sortedSet, Node<T> currentNode) {
        sortedSet.add(currentNode.value);
        if (currentNode.right != null) {
            newNodeAddition(sortedSet, currentNode.right);
        }
        if (currentNode.left != null) {
            newNodeAddition(sortedSet, currentNode.left);
        }
    }


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    // Трудоемкость T = O(n * log(n))
    // Ресурсоемкость R = O(n)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> sortedSet = new TreeSet<>();
        tailSetRecursive(fromElement, sortedSet, root);
        return sortedSet;
    }

    private void tailSetRecursive(T fromElement, SortedSet<T> sortedSet, Node<T> currentNode) {
        int edgeComparison = currentNode.value.compareTo(fromElement);
        if (edgeComparison == -1) {
            if (currentNode.right != null) {
                tailSetRecursive(fromElement, sortedSet, currentNode.right);
            }
        } else {
            sortedSet.add(currentNode.value);
            if (currentNode.right != null) {
                newNodeAddition(sortedSet, currentNode.right);
            }
            if (currentNode.left != null) {
                tailSetRecursive(fromElement, sortedSet, currentNode.left);
            }
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
