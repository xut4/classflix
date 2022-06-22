package cute.finalproject;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class JsonHandler {
   //i is 1 for teacher,2 for class
   public static ArrayList<Course> getList(int i) throws IOException{
      //拆解json
      String fileName = "courses.json";
      InputStream is = new FileInputStream(fileName);
      BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      String line = buf.readLine();
      StringBuilder sb = new StringBuilder();
      while (line != null) {
         sb.append(line).append("\n");
         line = buf.readLine();
      }
      buf.close();
      String jsonStr = sb.toString();
      Gson gson = new Gson();
      java.lang.reflect.Type userListType = new TypeToken<ArrayList<Course>>(){}.getType();
      ArrayList<Course> courses = gson.fromJson(jsonStr, userListType);
      
      ArrayList<Course> array = new ArrayList<>();
       for(Course x : courses){
          Boolean flag = false;
          for(Course y : array){
            if(i==1){ //teacher
               if(x.getProfessor().equals(y.getProfessor())) { //判斷是否重複
                  flag = true;
                  break;
               }
            }
            else if(i==2){ //class
               if(x.getClassName().equals(y.getClassName())) { //判斷是否重複
                  flag = true;
                  break;
               }    
            }
          }
          if(flag == false) {
             array.add(x); //放進array
          }
       }
      return array;
   }
   public static List<String> get(String teacher) throws IOException{ //用老師來找有的課，用
      String fileName = "courses.json";
      InputStream is = new FileInputStream(fileName);
      BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      String line = buf.readLine();
      StringBuilder sb = new StringBuilder();
      while (line != null) {
         sb.append(line).append("\n");
         line = buf.readLine();
      }
      buf.close();
      String jsonStr = sb.toString();
      Gson gson = new Gson();
      java.lang.reflect.Type userListType = new TypeToken<ArrayList<Course>>(){}.getType();
      ArrayList<Course> courses = gson.fromJson(jsonStr, userListType);
      List<Course> result;
      ArrayList<String> ans = new ArrayList<>();

      ArrayList<Course> array = new ArrayList<>();
      for(Course x : courses){
         Boolean flag = false;
         for(Course y : array){
            if(x.getClassName().equals(y.getClassName())) { //判斷是否重複
               flag = true;
               break;
            }
         }
         if(flag == false) {
            array.add(x);
         }
      }
      //在所有課程中篩選出表單中屬於"老師"開的課
      result = array.stream().filter((Course c) -> c.getProfessor().indexOf(teacher) > -1).collect(Collectors.toList());
      for(Course z : result){
         ans.add(z.getClassName());
      }
      return ans;
    }
}