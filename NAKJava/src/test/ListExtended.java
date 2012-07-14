package test;

import java.util.List;

public interface ListExtended<E> extends List<E> {

	public ListExtended<E> select(Selector selector);
}
