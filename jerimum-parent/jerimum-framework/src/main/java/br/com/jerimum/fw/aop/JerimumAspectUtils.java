package br.com.jerimum.fw.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public abstract class JerimumAspectUtils {

	private static final int MAX_ARRAY_DISPLAY = 20;
	private static final int MAX_LENGTH_STRING = 200;

	private JerimumAspectUtils() {

	}

	/**
	 * 
	 * @param jp
	 * @return
	 */
	public static Logger getLogger(JoinPoint jp) {
		return LoggerUtils.getLogger(jp.getTarget().getClass());
	}

	/**
	 * 
	 * @param jp
	 * @return
	 */
	public static String serviceName(ProceedingJoinPoint jp) {
		return jp.getTarget().getClass().getSimpleName();
	}

	/**
	 * 
	 * @param jp
	 * @return
	 */
	public static String methodName(JoinPoint jp) {
		return jp.getSignature().getName();
	}

	/**
	 * 
	 * @param jp
	 * @return
	 */
	public static String fullMethodName(JoinPoint jp) {
		return jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
	}

	/**
	 * 
	 * @param jp
	 * @return
	 */
	public static String className(JoinPoint jp) {
		return jp.getTarget().getClass().getName();
	}

	/**
	 * Returns the executed method name.
	 * 
	 * @param jp
	 * @return {@link string}
	 */
	@SuppressWarnings("rawtypes")
	public static String getMethodName(ProceedingJoinPoint jp) {

		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		Class[] parameterTypes = methodSignature.getParameterTypes();
		String[] parameterNames = methodSignature.getParameterNames();

		StringBuilder method = new StringBuilder();
		method.append(methodSignature.getReturnType().getSimpleName()).append(" ");
		method.append(jp.getTarget().getClass().getSimpleName()).append(".");
		method.append(methodSignature.getMethod().getName()).append("(");
		if (ArrayUtils.isNotEmpty(parameterTypes)) {
			for (int i = 0; i < parameterTypes.length; i++) {
				method.append(parameterTypes[i].getSimpleName()).append(" ").append(parameterNames[i]);
				if (i + 1 < parameterTypes.length) {
					method.append(", ");
				}
			}
		}
		method.append(")");
		return method.toString();
	}

	/**
	 * Returns the parameters for the executed method.
	 * 
	 * @param jp
	 * @return {@link String}
	 */
	@SuppressWarnings("rawtypes")
	public static String getArgumentValues(ProceedingJoinPoint jp) {

		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		Class[] parameterTypes = methodSignature.getParameterTypes();
		String[] parameterNames = methodSignature.getParameterNames();
		Object[] values = jp.getArgs();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {

			Object arg = values[i];
			sb.append(parameterTypes[i].getSimpleName()).append(" ").append(parameterNames[i]).append(": ").append(arg);
			if (i + 1 < parameterTypes.length) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param jp
	 * @param type
	 * @return
	 */
	public static Object getArgOfType(JoinPoint jp, Class<?> type) {

		Object[] args = jp.getArgs();

		for (Object arg : args) {
			if (arg != null && arg.getClass().isAssignableFrom(type)) {
				return arg;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param jp
	 * @param type
	 * @return
	 */
	public static Object[] getArgsOfType(JoinPoint jp, Class<?> type) {

		Object[] args = jp.getArgs();
		List<Object> argsOfType = new ArrayList<Object>();

		for (Object arg : args) {
			if (arg != null && arg.getClass().isAssignableFrom(type)) {
				argsOfType.add(arg);
			}
		}

		return argsOfType.toArray();
	}

	/**
	 * 
	 * @param args
	 * @param sb
	 */
	public static void appendArguments(Object[] args, StringBuilder sb) {
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (i > 0) {
				sb.append(',');
			}
			sb.append(displayObject(arg));
		}
	}

	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static String displayObject(Object arg) {
		if (arg == null) {
			return "<null>";
		}
		if (arg instanceof String || arg instanceof Character || arg instanceof Boolean || arg instanceof Number) {
			return StringUtils.abbreviate(arg.toString(), MAX_LENGTH_STRING);
		}
		if (arg instanceof Date) {
			return DateFormatUtils.format((Date) arg, "dd/MM/yyyy HH:mm:ss");
		}
		if (arg.getClass().isArray()) {
			int length = Array.getLength(arg);
			if (length > MAX_ARRAY_DISPLAY) {
				return "LargeArray[size=" + length + "]";
			}
		}
		if (hasToString(arg)) {
			return StringUtils.abbreviate(arg.toString(), MAX_LENGTH_STRING);
		}
		return StringUtils.abbreviate(ToStringBuilder.reflectionToString(arg, ToStringStyle.SHORT_PREFIX_STYLE),
				MAX_LENGTH_STRING);
	}

	public static boolean hasToString(Object o) {
		try {
			if (o != null) {
				Method m = o.getClass().getDeclaredMethod("toString", new Class[0]);
				return m != null;
			}
		} catch (NoSuchMethodException e) {
			// Do nothing
		}
		return false;
	}

}
