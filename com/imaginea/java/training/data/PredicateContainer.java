/**
 * 
 */
package com.imaginea.java.training.data;

import java.util.function.Predicate;

/**
 * @author srinivask
 *
 */
public class PredicateContainer {
	public final static Predicate<String> STRING_EMPTY_CHECK_PREDICATE = (String s) -> null == s || s.trim().isEmpty();

	public Predicate<String> stringEmptyCheckPredicate() {
		return STRING_EMPTY_CHECK_PREDICATE;
	}
}
