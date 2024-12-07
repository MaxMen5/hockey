package ru.mendeleev.hockey.utils;

import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;

@Log4j
public class Beans {
	private static ApplicationContext CONTEXT;

	public static void init(ApplicationContext ctx) {
		CONTEXT = ctx;
	}

	public static <T> T getBean(Class<T> beanType) {
		return CONTEXT.getBean(beanType);
	}

}
