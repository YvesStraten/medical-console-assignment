package com.yvesstraten.medicalconsole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * This interface acts a marker on fields. When used 
 * on a class, the field is made legal to be 
 * edited
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Editable {
	// Message that should be output when editing
	String message() default "";
	/* Name of setter, if it is different than 
	 the field's name */
	String setter() default "";
}
