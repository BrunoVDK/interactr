package test.unit.purecollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import interactr.cs.kuleuven.be.purecollections.PList;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
public class PListTest {

	@Test
	public void testEmpty() {
		PList<Integer> list = PList.empty();
		assertThrows(NoSuchElementException.class,
				()->{list.getFirst();});
		assertThrows(NoSuchElementException.class,
				()->{list.minusFirst();});
	}

	@Test
	public void testPlusAndMinusFirst() {
		PList<Integer> list = PList.empty();
		for (int i = 0; i < 1000000; i++)
			list = list.plus(i);
		for (int i = 0; i < 1000000; i++) {
			Assertions.assertEquals(i, (int)list.getFirst());
			list = list.minusFirst();
		}
		Assertions.assertSame(PList.empty(), list);
	}
	
	@Test
	public void testSecondaryMethods() {
		PList<Integer> list = PList.<Integer>empty().plus(1).plus(2).plus(3).plus(4);
		
		Assertions.assertEquals("[1, 2, 3, 4]", list.toString());
		Assertions.assertEquals(Arrays.asList(new Integer[] {1,2,3,4}), list);
		Assertions.assertEquals(list, Arrays.asList(new Integer[] {1,2,3,4}));
		Assertions.assertEquals(Arrays.asList(new Integer[] {1,2,3,4}).hashCode(), list.hashCode());
		Assertions.assertFalse(list.equals(Arrays.asList(new Integer[] {1,2,3})));
		Assertions.assertFalse(list.equals(Arrays.asList(new Integer[] {1,2,3,5})));
		
		Assertions.assertEquals(4, list.size());
		Assertions.assertFalse(list.isEmpty());
		Integer[] xs = {1, 2, 3, 4};
		Assertions.assertArrayEquals(xs, new ArrayList<Integer>(list).toArray()); // tests the iterator
		Assertions.assertArrayEquals(xs, list.toArray());
		Assertions.assertArrayEquals(xs, list.toArray(new Integer[0]));
		Assertions.assertArrayEquals(new Integer[] {1,2,4}, list.minus((Integer)3).toArray());
		Assertions.assertArrayEquals(new Integer[] {1,3,4}, list.minus(1).toArray());
		Assertions.assertArrayEquals(new Integer[] {1,0,2,3,4}, list.plus(1, 0).toArray());
		Assertions.assertArrayEquals(new Integer[] {0,1,2,3,4}, list.plus(0, 0).toArray());
		Assertions.assertArrayEquals(new Integer[] {0,1,2,3,4}, list.plusFirst(0).toArray());
		Assertions.assertArrayEquals(new Integer[] {-2,-1,0,1,2,3,4}, list.plusFirst(0).plusFirst(-1).plusFirst(-2).toArray());
		Assertions.assertArrayEquals(new Integer[] {1,2,5,4}, list.with(2, 5).toArray());
		Assertions.assertArrayEquals(new Integer[] {1,2,3,4,5,6}, list.plusAll(Arrays.asList(new Integer[]{5,6})).toArray());
		Assertions.assertArrayEquals(new Integer[] {1,3}, list.minusAll(Arrays.asList(new Integer[]{2,4})).toArray());
		Assertions.assertArrayEquals(new Integer[] {2,3}, list.subList(1,3).toArray());
		Assertions.assertEquals(0, list.indexOf(1));
		Assertions.assertEquals(-1, list.indexOf(5));
		Assertions.assertEquals(4, list.plus(1).lastIndexOf(1));
		Assertions.assertEquals(-1, list.lastIndexOf(5));
		Assertions.assertTrue(list.contains(3));
		Assertions.assertFalse(list.contains(-100));
		Assertions.assertEquals((Object)2, list.get(1));
		Assertions.assertTrue(list.containsAll(Arrays.asList(new Integer[] {1, 4})));
		Assertions.assertFalse(list.containsAll(Arrays.asList(new Integer[] {1, 5})));
		
		ListIterator<Integer> iterator = list.listIterator();
		Assertions.assertEquals(0, iterator.nextIndex());
		Assertions.assertEquals(-1, iterator.previousIndex());
		Assertions.assertEquals(true, iterator.hasNext());
		Assertions.assertEquals(false, iterator.hasPrevious());
		Assertions.assertEquals((Integer)1, iterator.next());
		Assertions.assertEquals(1, iterator.nextIndex());
		Assertions.assertEquals(0, iterator.previousIndex());
		Assertions.assertEquals(true, iterator.hasNext());
		Assertions.assertEquals(true, iterator.hasPrevious());
		Assertions.assertEquals((Integer)1, iterator.previous());
		Assertions.assertEquals(0, iterator.nextIndex());
		
		iterator = list.listIterator(4);
		Assertions.assertEquals(4, iterator.nextIndex());
		Assertions.assertEquals(3, iterator.previousIndex());
		Assertions.assertEquals(false, iterator.hasNext());
		Assertions.assertEquals(true, iterator.hasPrevious());
		Assertions.assertEquals((Integer)4, iterator.previous());
		Assertions.assertEquals(3, iterator.nextIndex());
		Assertions.assertEquals(2, iterator.previousIndex());
		Assertions.assertEquals(true, iterator.hasNext());
		Assertions.assertEquals(true, iterator.hasPrevious());
		Assertions.assertEquals((Integer)4, iterator.next());
		Assertions.assertEquals(4, iterator.nextIndex());
	}
	
}
