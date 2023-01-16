package myObject;

//課程
public class Course {
    private int ID; //課程ID
    /*基本資訊*/
    private String HREF; //課程連結
    private String DATE; //開課日期
    private String NAME; //課程名稱
    private String LECTURER; //授課講師
    private String SCHOOL; //授課學校
    private String WEEK; //授課星期
    private String CLASSIFICATION; //課程分類
    private String STATUS; //開課狀況
    private Details DETAILS; //詳細資訊
    private String LIKECOURSE; //(是否為)喜歡課程
    private String MYCOURSE; //(是否為)我的課程

    public Course(){
        /*test*/
        CLASSIFICATION = "CLASS";
        NAME = "NAME";

        DETAILS = new Details();
    }

    /*詳細資訊*/
    public class Details{
        private String PERIOD; //課程期別
        private String FEES; //課程費用
        private String OTHER_FEES; //其他費用
        private String ADDRESS; //授課地址
        private String IDEAGOAL; //理念目標
        private String METHOD; //教學方式
        private String TEACHER_PRESENTATION; //師資介紹
        private String REQUIRE; //選課要求
        private String REMARK; //備註
        private String SING_UP_HREF; //報名連結
        private WeeklyContent[] WEEKLY_CONTENTS = new  WeeklyContent[18]; //各週次課程內容

        public String getPERIOD() {
            return PERIOD;
        }

        public void setPERIOD(String PERIOD) {
            this.PERIOD = PERIOD;
        }

        public String getFEES() {
            return FEES;
        }

        public void setFEES(String FEES) {
            this.FEES = FEES;
        }

        public String getOTHER_FEES() {
            return OTHER_FEES;
        }

        public void setOTHER_FEES(String OTHER_FEES) {
            this.OTHER_FEES = OTHER_FEES;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getIDEAGOAL() {
            return IDEAGOAL;
        }

        public void setIDEAGOAL(String IDEAGOAL) {
            this.IDEAGOAL = IDEAGOAL;
        }

        public String getMETHOD() {
            return METHOD;
        }

        public void setMETHOD(String METHOD) {
            this.METHOD = METHOD;
        }

        public String getTEACHER_PRESENTATION() {
            return TEACHER_PRESENTATION;
        }

        public void setTEACHER_PRESENTATION(String TEACHER_PRESENTATION) {
            this.TEACHER_PRESENTATION = TEACHER_PRESENTATION;
        }

        public String getREQUIRE() {
            return REQUIRE;
        }

        public void setREQUIRE(String REQUIRE) {
            this.REQUIRE = REQUIRE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getSING_UP_HREF() {
            return SING_UP_HREF;
        }

        public void setSING_UP_HREF(String SING_UP_HREF) {
            this.SING_UP_HREF = SING_UP_HREF;
        }

        public WeeklyContent[] getWEEKLY_CONTENTS() {
            return WEEKLY_CONTENTS;
        }

        public void setWEEKLY_CONTENTS(WeeklyContent[] WEEKLY_CONTENTS) {
            this.WEEKLY_CONTENTS = WEEKLY_CONTENTS;
        }
    }

    /*各週次課程內容*/
    class WeeklyContent{
        private String WEEKS; //週次
        private String THEME; //主題
        private String CONTENT; //內容

        public String getWEEKS() {
            return WEEKS;
        }

        public void setWEEKS(String WEEKS) {
            this.WEEKS = WEEKS;
        }

        public String getTHEME() {
            return THEME;
        }

        public void setTHEME(String THEME) {
            this.THEME = THEME;
        }

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHREF() {
        return HREF;
    }

    public void setHREF(String HREF) {
        this.HREF = HREF;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLECTURER() {
        return LECTURER;
    }

    public void setLECTURER(String LECTURER) {
        this.LECTURER = LECTURER;
    }

    public String getSCHOOL() {
        return SCHOOL;
    }

    public void setSCHOOL(String SCHOOL) {
        this.SCHOOL = SCHOOL;
    }

    public String getWEEK() {
        return WEEK;
    }

    public void setWEEK(String WEEK) {
        this.WEEK = WEEK;
    }

    public String getCLASSIFICATION() {
        return CLASSIFICATION;
    }

    public void setCLASSIFICATION(String CLASSIFICATION) {
        this.CLASSIFICATION = CLASSIFICATION;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Details getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(Details DETAILS) {
        this.DETAILS = DETAILS;
    }

    public String getLIKECOURSE() {
        return LIKECOURSE;
    }

    public void setLIKECOURSE(String LIKECOURSE) {
        this.LIKECOURSE = LIKECOURSE;
    }

    public String getMYCOURSE() {
        return MYCOURSE;
    }

    public void setMYCOURSE(String MYCOURSE) {
        this.MYCOURSE = MYCOURSE;
    }
}
