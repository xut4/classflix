package cute.finalproject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InformationJsonHandler {

    private static String jsonFileName = "courses.json";
	private List<Course> coursesList;

    public String produceStringFromFile(String fileName) throws IOException { //轉成String
		InputStream is = new FileInputStream(fileName);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		buf.close();
		return sb.toString();
	}

    public List<Course> convertToObjects(String jsonData) { //轉成Course

		Gson gson = new Gson();
		ArrayList<Course> clinicList = new ArrayList<Course>();

		try {
			Type listType = new TypeToken<List<Course>>() {
			}.getType();
			clinicList = gson.fromJson(jsonData, listType);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}
		return clinicList;
	}

    public List<Course> findCourses(String professor, String className) {  //關鍵字找課
		List<Course> matchingElements = coursesList.stream().filter(str -> str.getProfessor().trim().contains(professor) && str.getClassName().trim().contains(className)).collect(Collectors.toList());

		return matchingElements;
	}

	public void initialize() throws IOException, URISyntaxException { //初始

		String courseDataJson = produceStringFromFile(jsonFileName);
		coursesList = convertToObjects(courseDataJson);
	}

}
