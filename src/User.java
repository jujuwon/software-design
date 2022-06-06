import java.util.*;
import javax.swing.JOptionPane;

public class User {
    DBManage db = new DBManage();
    EveryTimeDBManage everyTimeDB = new EveryTimeDBManage();
    int enterType;
    ArrayList<String> createdChatRoom;
    String dept;
    Boolean searchResult;
    String matchDeptResult;
    String studno;
    String matchStudnoResult;
    UserData userData; 
    String userID;  // user가 채팅방 시스템을 사용하기 전에 userID는 알고 있음(사전조건)
    String pwd1;
    String pwd2;

    public int selectEnterType(){//입장할 채팅방 종류 선택 메소드
        Scanner sc = new Scanner(System.in);

        System.out.println("입장할 채팅방의 종류를 선택해주세요.\n0 : 학과 채팅방, 1 : 학과 채팅방");
        System.out.print("채팅방 종류 입력 : ");
        int result = sc.nextInt(); 
        sc.close();                              
        return result;
    }

    public ArrayList<String> getCreatedChatRoom(){//생성된 채팅방 목록 가져오는 메소드
        ArrayList<String> createdChatRoom = new ArrayList<String>();     
        String query = "SELECT name FROM chatRoom WHERE isCreated  = 1;";//query문 select chatRoom from chatroom
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            createdChatRoom.add(token);
        }
        return createdChatRoom;
    }

    public String getDept(UserData userData){//유저의 학과를 가져오는 메소드
        String query = "SELECT dept FROM userData WHERE userID = '" + userData.userID + "';";
        return everyTimeDB.startQuery(query);
    }

    public Boolean searchUserDeptChatRoom(String dept){//유저의 학과에 해당하는 채팅방을 찾는 메소드
        ArrayList<String> createdChatRoom = new ArrayList<String>();
        int totalElements = createdChatRoom.size();
        String new_dept = "dept." + dept;
        for(int i = 0; i < totalElements; i++){
            if(new_dept == createdChatRoom.get(i)){
                return true;
            }
        }
        return false;
    }

    public String matchDept(){//유저를 학과 채팅방에 매칭하는 메소드
        userData.matchDeptResult = dept;
        String matchDeptResult = userData.matchDeptResult;
        return matchDeptResult;
    }

    public void saveMatchDeptResult(String matchDeptResult){//유저를 학과 채팅방에 매칭한 결과를 채팅방에 저장하는 메소드
        String query = "UPDATE user SET matchDeptResult = '" + matchDeptResult + "' WHERE userID = '" + userData.userID + "';";
        db.startQuery(query);
    }

    public String getStudno(UserData userData){//유저의 학번을 가져오는 메소드
        String query = "SELECT studno FROM userData WHERE userID = '" + userData.userID + "';";
        return everyTimeDB.startQuery(query);
    }

    public Boolean searchUserStudnoChatRoom(String studno){//유저의 학번에 해당하는 채팅방을 찾는 메소드
        ArrayList<String> createdChatRoom = new ArrayList<String>();
        int totalElements = createdChatRoom.size();
        String new_studno = "studno." + studno;
        for(int i = 0; i < totalElements; i++){
            if(new_studno == createdChatRoom.get(i)){
                return true;
            }
        }
        return false;
    }

    public String matchStudno(){//유저를 학번 채팅방에 매칭하는 메소드
        userData.matchStudnoResult = studno;
        String matchStudnoResult = userData.matchStudnoResult;
        return matchStudnoResult;
    }

    public void saveMatchStudnoResult(String matchDeptResult){//유저를 학번 채팅방에 매칭한 결과를 채팅방에 저장하는 메소드
        String query = "UPDATE user SET matchStudnoResult = '" + matchStudnoResult + "' WHERE userID = '" + userData.userID + "';";
        db.startQuery(query);
    }

    public String getMatchDeptResult(UserData userData){//유저의 매칭된 학과 채팅방이 있으면 그 채팅방을 가져오는 메소드
        String query = "SELECT matchDeptResult FROM user WHERE matchDeptResult = '" + userData.dept + "';";
        return db.startQuery(query);
    }

    public String getMatchStudnoResult(UserData userData){//유저의 매칭된 학번 채팅방이 있으면 그 채팅방을 가져오는 메소드
        String query = "SELECT matchStudnoResult FROM user WHERE matchStudnoResult = '" + userData.studno + "';";
        return db.startQuery(query);
    }
    
    public String showInputPwd(){//비밀번호 입력창을 보여주는 메소드
        return JOptionPane.showInputDialog("비밀번호를 입력하세요.");
    }
    
    public String getPwd(UserData userData){//유저의 채팅방 비밀번호를 가져오는 메소드
        String query = "SELECT pwd FROM user WHERE userID = '" + userData.userID + "';";
        return db.startQuery(query);
    }
    
    public Boolean comparePwd(String pwd1, String pwd2){//비밀번호 비교하는 메소드
        if(pwd1 == pwd2){
            return true;
        }
        else return false;
    }
    
    public void showError(){//오류 표시하는 메소드
        System.out.println("Error");
    }
    
    public String setFirstPwd(){//설정할 비밀번호를 입력하는 메소드
        Scanner sc = new Scanner(System.in);

        System.out.println("설정할 비밀번호를 입력하세요 : ");
        String result = sc.next();
        sc.close();                               
        return result;
    }

    public String setSecondPwd(){//비밀번호 재확인 메소드
        Scanner sc = new Scanner(System.in);

        System.out.println("입력한 비밀번호를 확인하기 위한 비밀번호를 입력하세요 : ");
        String result = sc.next();
        sc.close();                               
        return result;
    }
    
    public void savePwd(UserData userData){//유저의 채팅방 비밀번호를 저장하는 메소드
        String query = "UPDATE user SET pwd = '" + userData.pwd + " WHERE userID = '" + userData.userID + "';"; 
        db.startQuery(query);
    }




//-------------------------------------------------------------U4 채팅방 매칭----------------------------------------------------------------------------------
    public void matchingChatRoom(){//채팅방 매칭
        createdChatRoom = new ArrayList<String>();
        userData = new UserData();
        userData.userID = userID;
        enterType = selectEnterType();                               //U4.Sequence 1,2 채팅방 종류 선택 및 반환
        if(enterType == 0){                                          //U4.Sequence alt 학과 채팅방 선택 시
            createdChatRoom = getCreatedChatRoom();                  //U4.Sequence 3,4 생성되어 있는 채팅방 목록 요청 및 반환
            dept = getDept(userData);                                //U4.Sequence 5,6 사용자의 학과를 요청 및 반환
            searchResult = searchUserDeptChatRoom(dept);             //U4.Sequence 7,8 사용자의 학과 채팅방이 생성되어 있는지 확인하고 결과를 반환
            if(searchResult == true){                                //U4.Sequence alt 채팅방이 생성되어 있을 경우
                matchDeptResult = matchDept();                       //U4.Sequence 9,10 사용자를 학과 채팅방에 매칭시키고 매칭 결과를 반환
                saveMatchDeptResult(matchDeptResult);                //U4.Sequence 11 채팅방 DB에 사용자의 매칭 결과를 저장
            }
            else if(searchResult == false){                          //U4.Sequence alt 채팅방이 생성되어 있지 않을 경우
                showError();                                         //U4.Sequence 12 오류를 표시
            }
        }
        else if(enterType == 1){                                     //U4.Sequence alt 학번 채팅방 선택 시
            createdChatRoom = getCreatedChatRoom();                  //U4.Sequence 13,14 생성되어 있는 채팅방 목록 요청 및 반환
            studno = getStudno(userData);                            //U4.Sequence 15,16 사용자의 학번을 요청 및 반환
            searchResult = searchUserStudnoChatRoom(studno);         //U4.Sequence 17,18 사용자의 학번 채팅방이 생성되어 있는지 확인하고 결과를 반환
            if(searchResult == true){                                //U4.Sequence alt 채팅방이 생성되어 있을 경우
                matchStudnoResult = matchStudno();                   //U4.Sequence 19,20 사용자를 학번 채팅방에 매칭시키고 매칭 결과를 반환
                saveMatchStudnoResult(matchStudnoResult);            //U4.Sequence 21 채팅방 DB에 사용자의 매칭 결과를 저장
            }
            else if(searchResult == false){                          //U4.Sequence alt 채팅방이 생성되어 있지 않을 경우
                showError();                                         //U4.Sequence 22 오류를 표시
            }
        }
    }
    
//-------------------------------------------------------------U4 채팅방 입장---------------------------------------------------------------------------------------------------
    public void enterChatRoom(){//채팅방 입장
        userData = new UserData();
        userData.userID = userID;
        enterType = selectEnterType();                               //U5.Sequence 1,2 채팅방 종류 선택 및 반환
        if(enterType == 0){                                          //U5.Sequence alt 학과 채팅방 선택 시
            matchDeptResult = getMatchDeptResult(userData);          //U5.Sequence 3,4 사용자의 학과 채팅방 매칭 결과를 요청 및 반환
        }
        else if(enterType == 1){                                     //U5.Sequence alt 학번 채팅방 선택 시
            matchStudnoResult = getMatchStudnoResult(userData);      //U5.Sequence 5,6 사용자의 학번 채팅방 매칭 결과를 요청 및 반환
        }
        if((enterType == 0 && matchDeptResult != null) || (enterType == 1 && matchStudnoResult != null)){ //매칭결과가 존재해야 채팅방 입장가능 추가
            if(userData.isLocked == true){                           //U5.Sequence alt 비밀번호가 설정되어 있을 경우
                pwd1 = showInputPwd();                               //U5.Sequence 7,8 비밀번호 입력창 표시하고 입력 및 반환
                pwd2 = getPwd(userData);                             //U5.Sequence 9,10 사용자의 비밀번호 요청 및 반환
                boolean isCorrect = comparePwd(pwd1, pwd2);          //U5.Sequence 11,12 입력한 비밀번호 일치하는지 비교 및 결과 반환
                if(isCorrect == true){                               //U5.Sequence alt 비밀번호가 일치할 경우
                    System.out.println("채팅방에 입장했습니다.");  //U5.Sequence 13 채팅방 표시
                }
                else if(isCorrect == false){                         //U5.Sequence alt 비밀번호가 일치하지 않을 경우
                    showError();                                     //U5.Sequence 14 오류 표시
                }
            }
            else if(userData.isLocked == false){                     //U5.Sequence alt 비밀번호가 설정되어 있지 않을 경우
                System.out.println("채팅방에 입장했습니다.");      //U5.Sequence 13 채팅방 표시
            }
        }
    }

//-------------------------------------------------------------U4 채팅방 잠금 설정---------------------------------------------------------------------------------------------------
    public void lockChatRoom(){//채팅방 잠금 설정                     //U8.Sequence 1 잠금 설정
        userData = new UserData();
        userData.userID = userID;
        pwd1 = setFirstPwd();                                        //U8.Sequence 2,3 설정하려는 비밀번호 입력 및 반환
        pwd2 = setSecondPwd();                                       //U8.Sequence 4,5 재확인하려는 비밀번호 입력 및 반환
        boolean isCorrect = comparePwd(pwd1, pwd2);                  //U8.Sequence 6,7 이력한 두 비밀번호가 일치하는지 비교 및 결과 반환
        if(isCorrect == true){                                       //U8.Sequence alt 비밀번호가 일치하는 경우
            savePwd(userData);                                       //U8.Sequence 8 비밀번호와 잠금 설정 저장
        }
    }
}
