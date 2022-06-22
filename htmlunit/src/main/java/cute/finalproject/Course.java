package cute.finalproject;

public class Course {
    private String classCode;
    private String professor;
    private String className;
    private String englishClassName;
    private String time;
    private String isEnglish;
    private String teachingMethod;
    private String evaluation;

    public Course(String classCode, String professor, String className, String englishClassName, String time, String isEnglish, String teachingMethod, String evaluation){
        
        this.className = className;
        this.englishClassName = englishClassName;
        this.isEnglish = isEnglish;
        this.teachingMethod = teachingMethod;
        this.evaluation = evaluation;
    }
    
    public String getClassCode(){
        return classCode;
    }

    public String getProfessor(){
        return professor;
    }

    public String getClassName(){
        return className;
    }

    public String getEnglishClassName(){
        return englishClassName;
    }

    public String getIsEnglish(){
        return isEnglish;
    }

    public String getTeachingMethod(){
        return teachingMethod;
    }

    public String getEvaluation(){
        return evaluation;
    }
    
    @Override
	public String toString() {
        return "Course [classcode=" + classCode + ", professor=" + professor + ", classname=" + className + ", englishClassName=" + englishClassName + ", time=" + time + ", isEnglish=" + isEnglish + ", teachingMethod=" + teachingMethod + ", evaluation=" + evaluation + "]";
	}
}
