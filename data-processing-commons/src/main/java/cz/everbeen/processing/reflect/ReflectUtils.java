package cz.everbeen.processing.reflect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility facade providing help with reflection-related tasks.
 *
 * @author darklight
 */
public final class ReflectUtils {

	/**
	 * Get all fields declared by this class (any visibility) and all of its superclasses up to the exclusive parent
	 * @param targetClass Class whose fields we're getting
	 * @param exclusiveParent The first superclass whose fields we're not including in the resulting field collection
	 * @return Collection of declared fields
	 */
	public static final Collection<Field> getDeclaredFieldsUpTo(Class<?> targetClass, Class<?> exclusiveParent) {
		final List<Field> fields = new LinkedList<Field>();
		for (Class<?> currentClass = targetClass; !currentClass.equals(exclusiveParent); currentClass = currentClass.getSuperclass()) {
			fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
		}
		return fields;
	}
}
