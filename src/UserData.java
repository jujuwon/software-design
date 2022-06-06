public class UserData {
    String userID; //userID 유저를 식별해서 유저 데이터를 가져오기 위해 추가
    String studno;
    String dept;
    String exitDate;
    Boolean isExit;
    String pwd;
    String matchDeptResult;
    String matchStudnoResult;
    Boolean isReport;
    Boolean isLocked;

    public UserData() {
        userID = null;
        studno = null;
        dept = null;
        exitDate = null;
        isExit = null;
        pwd = null;
        matchDeptResult = null;
        matchStudnoResult = null;
        isReport = null;
        isLocked = null;
    }
}
