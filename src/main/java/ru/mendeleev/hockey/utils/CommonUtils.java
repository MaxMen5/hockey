package ru.mendeleev.hockey.utils;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Класс статических утилитных методов.
 */
public final class CommonUtils {

	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	private CommonUtils() {
	}

	/**
	 * Преобразование объектов в строку, где между каждой парой элементов стоит разделитьель.
	 * @param objs      объекты для преобразования в строку
	 * @param separator разделитель
	 */
	public static String collectionToString(Collection objs, String separator) {
		return collectionToString(objs, separator, "");
	}

	/**
	 * Преобразование объектов в строку, где между каждой парой элементов стоит разделитьель.
	 * @param objs      объекты для преобразования в строку
	 * @param separator разделитель
	 * @param borders   строка, вставляемая перед и после каждого элемента в результирующей строке (например, символ "'")
	 */
	public static String collectionToString(Collection objs, String separator, String borders) {
		if (objs == null || objs.isEmpty())
			return "";

		borders = (borders == null) ? "" : borders;

		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			sb.append(separator).append(borders).append(obj).append(borders);
		}

		return sb.substring(separator.length());
	}

	/**
	 * Получение панели, на которой компоненты расположены друг под другом в порядке, заданном в коллекции.
	 * @param components компоненты
	 */
	public static JPanel getPanelFilledWithComponents(Collection<Component> components) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel currPanel = mainPanel;
		for (Component component : components) {
			currPanel.add(component, BorderLayout.NORTH);
			JPanel newPanel = new JPanel(new BorderLayout());
			currPanel.add(newPanel, BorderLayout.CENTER);
			currPanel = newPanel;
		}
		return mainPanel;
	}

	/**
	 * Установка окна по центру экрана.
	 * @param window окно
	 */
	public static void setCenterLocation(Window window) {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (screenSize.width - window.getWidth()) / 2;
		final int y = (screenSize.height - window.getHeight()) / 2;
		window.setLocation(x, y);
	}

	/**
	 * Создание панели-разделителя.
	 * @param bgColor фон панели-разделителя
	 * @param height  высота панели-разделителя
	 */
	public static JPanel prepareSeparator(Color bgColor, float height) {
		return prepareTitlePanel(null, bgColor, height);
	}

	/**
	 * Создание панели-заголовка.
	 * @param text     текст заголовка
	 * @param bgColor  цвет фона панели
	 * @param fontSize размер шрифта текстазаголовка
	 */
	public static JPanel prepareTitlePanel(String text, Color bgColor, float fontSize) {
		JPanel panel = new JPanel(new BorderLayout());
		if (bgColor != null) {
			panel.setBackground(bgColor);
		}

		JLabel lbTitle = new JLabel();
		if (text != null) {
			lbTitle.setText(text);
		} else {
			lbTitle.setText("a");
			lbTitle.setFont(lbTitle.getFont().deriveFont(fontSize));
		}
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lbTitle, BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Получение запроса последних вставленных записей в таблицу (ТОЛЬКО ДЛЯ MySQL!!!).
	 * @param tableName название таблицы (без схемы; по умолчанию brightdb)
	 * @return запрос
	 */
	public static String getInsertedRowsMysqlQuery(String tableName) {
		return  "select " +
					"* " +
				"from " +
					"brightdb." + tableName + " " +
				"where 1=1 " +
					"and id between LAST_INSERT_ID() and (select max(id) from brightdb." + tableName + ")";
	}

	/**
	 * Получение размеров окна с зазорами по ширине и длине отностиельно размеров экрана монитора.
	 * @param widthShift зазор по ширине
	 * @param heightShift зазор по высоте
	 * @return размеры окна
	 */
	public static Dimension prepareWindowSizeWithShifts(int widthShift, int heightShift) {
		return new Dimension(
				(int) SCREEN_SIZE.getWidth() - widthShift,
				(int) SCREEN_SIZE.getHeight() - heightShift
		);
	}

}
