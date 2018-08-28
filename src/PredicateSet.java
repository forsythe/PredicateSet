import java.util.Collection;
import java.util.function.Predicate;

public class PredicateSet<E> {

    private Predicate<E> rootPredicate = e -> false;

    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        try {
            return rootPredicate.test((E) o);
        } catch (ClassCastException cce) {
            return false;
        }
    }

    public boolean add(E e) {
        if (contains(e))
            return false;
        Predicate<E> newPredicate = e::equals;
        rootPredicate = newPredicate.or(rootPredicate);
        size++;
        return true;
    }

    public boolean remove(Object o) {
        try {
            if (!contains((E) o))
                return false;
        } catch (ClassCastException cce) {
            return false;
        }
        E e = (E) o;
        Predicate<E> newPredicate = e::equals;
        rootPredicate = newPredicate.negate().and(rootPredicate);
        size--;
        return true;
    }

    public boolean containsAll(Collection<? extends E> c) {
        return c.stream().allMatch(this::contains);
    }

    public boolean addAll(Collection<? extends E> c) {
        var changed = false;
        for (E e : c) {
            if (add(e)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean removeAll(Collection<? extends E> c) {
        var changed = false;
        for (E e : c) {
            if (remove(e)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean intersect(Collection<? extends E> c) {
        PredicateSet<E> intersection = new PredicateSet<>();

        for (var x : c) {
            try {
                if (contains(x)) {
                    intersection.add((E) x);
                }
            } catch (ClassCastException ignored) {
            }
        }
        var changed = this.size != intersection.size;

        this.rootPredicate = intersection.rootPredicate;
        this.size = intersection.size;

        return changed;
    }

    public void clear() {
        this.size = 0;
        rootPredicate = e -> false;
    }
}
