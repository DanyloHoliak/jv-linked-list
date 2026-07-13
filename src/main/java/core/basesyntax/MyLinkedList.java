package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        T item;
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = last = newNode;
        }else  {
            Node<T> newLastNode = new Node<>(last, value, null);
            last.next = newLastNode;
            last = newLastNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = last = newNode;
            size++;
            return;
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev == null) {
            current.prev = newNode;
            first = newNode;
        }else {
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T e : list) {
            add(e);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            T oldValue = last.item;
            last = null;
            first = null;
            size--;
            return oldValue;
        }
        if (index == 0) {
            T oldValue = first.item;
            first = first.next;
            first.prev = null;
            size--;
            return oldValue;
        }
        if (index == size - 1) {
            T oldValue = last.item;
            Node<T> previous = last.prev;
            previous.next = null;
            last = previous;
            size--;
            return oldValue;
        }
        Node<T> current = getNode(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node<T> current = first;
        while (current != null) {
            if (object == null && current.item == null) {
                remove(index);
                return  true;
            }
            if (object != null && object.equals(current.item)) {
                remove(index);
                return true;
            }
            index++;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }
}
