package Assignment;

public class QuizInfo {
    private int quiz_ID;
    private String quiz_Title;
    private String quiz_Description;
    private String quiz_Theme;
    private String quiz_content;

    public QuizInfo(int quiz_ID, String quiz_Title, String quiz_Description,String quiz_Theme,String quiz_content){
        this.quiz_ID = quiz_ID;
        this.quiz_Title = quiz_Title;
        this.quiz_Description = quiz_Description;
        this.quiz_Theme = quiz_Theme;
        this.quiz_content = quiz_content;
    }
    public String getQuiz_Title() {
        return quiz_Title;
    }

    public void setQuiz_Title(String quiz_Title) {
        this.quiz_Title = quiz_Title;
    }

    public String getQuiz_Description() {
        return quiz_Description;
    }

    public void setQuiz_Description(String quiz_Description) {
        this.quiz_Description = quiz_Description;
    }

    public String getQuiz_Theme() {
        return quiz_Theme;
    }

    public void setQuiz_Theme(String quiz_Theme) {
        this.quiz_Theme = quiz_Theme;
    }

    public String getQuiz_content() {
        return quiz_content;
    }

    public void setQuiz_content(String quiz_content) {
        this.quiz_content = quiz_content;
    }

    public int getQuiz_ID(){
        return quiz_ID;
    }

    public void setQuiz_ID(int quiz_ID) {
        this.quiz_ID = quiz_ID;
    }
}
