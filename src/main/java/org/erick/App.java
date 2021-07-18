package org.erick;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.erick.domain.Student;
import org.erick.util.DBUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {

	public static void main(String[] args) {
		executeScriptFile();

		try (Session session = DBUtil.getSessionFactory().openSession()) {
			List<Student> students = session.createQuery("from Student", Student.class).list();
			students.forEach(s -> System.out.println(s.getFirstName()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void executeScriptFile() {
		String script = openScriptFile();
		System.out.println(script);
		if (script == null || script.equals("")) {
			return;
		}
		Transaction transaction = null;
		try (Session session = DBUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createNativeQuery(script).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String openScriptFile() {
		String content = "";
		try {
			File file = new File("D:\\dev\\script.sql");
			FileInputStream fis = new FileInputStream(file);
			System.out.println("file content: ");
			int r = 0;
			while ((r = fis.read()) != -1) {
				content += (char) r;
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
