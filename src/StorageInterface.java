public interface StorageInterface<E extends Comparable<E>> {
   boolean add(E x);
   boolean find(E x);
   boolean includesNull();
   boolean delete(E x);
}
