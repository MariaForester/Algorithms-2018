package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        private T value;

        T getValue() {
            return value;
        }

        void setValue(T valueToEstablish) {
            this.value = valueToEstablish;
        }

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
    // Трудоемкость Т = О(N) в худшем случае, O(logN) в среднем
    // Ресурсоемкость R = O(1)
    @Override
    public boolean remove(Object o) {
        if (root == null) {
            return false;
        }
        Node<T> parentNode = root;
        Node<T> currentNode = root;
        int comparisonValue;
        while ((comparisonValue = currentNode.getValue().compareTo((T) o)) != 0) {
            parentNode = currentNode;
            if (comparisonValue > 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            if (currentNode == null) {
                return false;
            }
        }
        delete(currentNode, parentNode);
        size--;
        return true;
    }


    private void delete(Node<T> currentNode, Node<T> parentNode) {
        if (currentNode.right != null && currentNode.left != null) {
            Node<T> succeedingNode = currentNode.right;
            Node<T> previousNode = currentNode;
            while (succeedingNode.left != null) {
                previousNode = succeedingNode;
                succeedingNode = succeedingNode.left;
            }
            currentNode.setValue(succeedingNode.getValue());
            if (previousNode == currentNode) {
                currentNode.right = succeedingNode.right;
            } else {
                previousNode.left = succeedingNode.right;
            }
            succeedingNode.right = null;
            succeedingNode = null;
        } else {
            if (currentNode.left != null) {
                moveToSubtree(parentNode, currentNode, currentNode.left);
            } else if (currentNode.right != null) {
                moveToSubtree(parentNode, currentNode, currentNode.right);
            } else {
                moveToSubtree(parentNode, currentNode, null);
            }
        }
    }

    private void moveToSubtree(Node<T> parentNode, Node<T> currentNode, Node<T> child) {
        if (parentNode == currentNode) {
            root = child;
        } else if (parentNode.left == currentNode){
            parentNode.left = child;
        } else {
            parentNode.right = child;
        }
        currentNode = null;
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
        private LinkedList<Node<T>> listForIteration;

        private BinaryTreeIterator() {
            listForIteration = new LinkedList<>();
            addToList(root);
        }

        private void addToList(Node<T> currentNode) {
            while (currentNode != null) {
                listForIteration.addFirst(currentNode);
                currentNode = currentNode.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        // Трудоемкость Т = О(1)
        // Ресурсоемкость R = О(N)
        private Node<T> findNext() {
            Node<T> nextNode = listForIteration.getFirst();
            listForIteration.removeFirst();
            return nextNode;
        }

        @Override
        public boolean hasNext() {
            return !listForIteration.isEmpty();
        }

        @Override
        public T next() {
            current = findNext();
            if (current.right != null) {
                addToList(current.right);
            }
            return current.getValue();
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        // Трудоемкоть Т = О(N) в худшем случае, O(logN) в среднем
        // Ресурсоемкость R = O(1)
        @Override
        public void remove() {
            Node<T> nodeToDelete = current;
            if (current != null) {
                Node<T> currentNode;
                if (hasNext()) {
                    currentNode = findNext();
                } else {
                    currentNode = find(last());
                }
                if (currentNode != null) {
                    delete(nodeToDelete, findCurrentParent(root, nodeToDelete.value));
                    size--;
                }
            }
        }

        private Node<T> findCurrentParent(Node<T> mainRoot, T valueForDeletion) {
            Node<T> current = mainRoot;
            while (current != null) {
                int comparison = valueForDeletion.compareTo(current.value);
                if (comparison == 0) return null;
                if (comparison < 0) {
                    if (valueForDeletion.compareTo(current.left.value) == 0) return current;
                    else current = current.left;
                } else {
                    if (valueForDeletion.compareTo(current.right.value) == 0) return current;
                    else current = current.right;
                }
            }
            return null;
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
    // Трудоемкость T = O(n)
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
        if (edgeComparison < 0) {
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
    // Трудоемкость T = O(n)
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
        if (edgeComparison < 0) {
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