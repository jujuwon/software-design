import java.util.*;
public class Administrator {
    DBManage db = new DBManage();
    int roomType;
    ArrayList<String> unCreatedDeptChatRoomList;
    String dept;
    ArrayList<String> unCreatedStudnoChatRoomList;
    String Studno;
    ArrayList<String> reportContent;
    ArrayList<String> CreatedChatRoom;
    ArrayList<String> userList;
    String SelectedChatRoom;
    UserData userData;
    ArrayList<UserData> userDataList; //새로 만듬 selectUserAndExit()함수에서 파라미터를 아무것도 안받기 떄문에 필드변수 필요함 
    int inqueryType;
    int sortType;

    //-----------------------------------------U1. 채팅방 생성----------------------------------------------------------------------
    public void createChatRoom(){                                           //U1.Sequence 1
        Scanner sc = new Scanner(System.in);
        System.out.println("생성할 채팅방의 종류를 선택해주세요.\n0 : 학과 채팅방, 1 : 학과 채팅방");
        System.out.print("채팅방 종류 입력 : ");
        roomType = sc.nextInt();                                            //U1.Sequence 2
        if(roomType == 0){
            unCreatedDeptChatRoomList = getUnCreatedDeptChatRoomList();     //U1.Sequence 3,4
            dept = selectDept();                                            //U1.Sequence 5,6
            saveCreatedDeptChatRoom(dept);                                  //U1.Sequence 7
        }
        else if(roomType == 1){
            unCreatedStudnoChatRoomList = getUnCreatedStudnoChatRoomList(); //U1.Sequence 8,9
            Studno = selectStudno();                                        //U1.Sequence 10,11
            saveCreatedStudnoChatRoom(Studno);                              //U1.Sequence 12
        }
        sc.close();
    }
    
    public ArrayList<String> getUnCreatedDeptChatRoomList(){//생성되지 않은 학과 채팅방 목록을 반환하는 메소드
        ArrayList<String> unCreatedDeptChatRoomList = new ArrayList<>();
        String query = new String("SELECT name FROM chatRoom WHERE isCreated = 0 AND name = LIKE 'dept%';");
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            unCreatedDeptChatRoomList.add(token);
        }
        return unCreatedDeptChatRoomList;
    }

    public String selectDept(){//생성되지 않은 학과 채팅방 목록 중에서 하나를 선택하는 메소드
        String dept;
        Scanner sc = new Scanner(System.in);
        System.out.println("아래의 목록에서 학과를 선택해주세요.");
        System.out.println(unCreatedDeptChatRoomList);
        System.out.print("학과 선택 : ");
        dept = sc.next();
        sc.close();
        return dept;
    }
    
    public void saveCreatedDeptChatRoom(String dept){
        String query = new String("UPDATE chatRoom SET isCreated = 1 WHERE name = LIKE '%" + dept + "';");
        db.startQuery(query);
    }

    public ArrayList<String> getUnCreatedStudnoChatRoomList(){//생성되지 않은 학번 채팅방 목록을 반환하는 메소드
        ArrayList<String> unCreatedStudnoChatRoomList = new ArrayList<>();
        String query = new String("SELECT name\nFROM chatRoom\nWHERE isCreated = 0 AND name = LIKE 'studno%';");
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            unCreatedStudnoChatRoomList.add(token);
        }
        return unCreatedStudnoChatRoomList;
    }
    
    public String selectStudno(){//생성되지 않은 학번 채팅방 목록 중에서 하나를 선택하는 메소드
        String Studno;
        Scanner sc = new Scanner(System.in);
        System.out.println("아래의 목록에서 학번을 선택해주세요.");
        System.out.println(unCreatedStudnoChatRoomList);
        System.out.print("학번 선택 : ");
        Studno = sc.next();
        sc.close();
        return Studno;
    }
    
    public void saveCreatedStudnoChatRoom(String Studno){//create를 보여주려면 chatRoom 스키마를 만들어야 됨(보류)(지금 상태는 체크만 해주고 있음.)
        String query = new String("UPDATE chatRoom SET isCreated = 1 WHERE studno = LIKE '%" + Studno + "';");
        db.startQuery(query);
    }

    public ArrayList<String> getCreatedChatRoom(){//생성된 학번&학과 채팅방 목록을 반환하는 메소드
        ArrayList<String> CreatedChatRoom = new ArrayList<>();
        String query = new String("SELECT name FROM chatRoom WHERE isCreated = 1;");
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            CreatedChatRoom.add(token);
        }
        return CreatedChatRoom;
    }
   
    public String selectCreatedChatRoom(){//생성된 학번&학과 채팅방 목록중에서 하나를 선택하는 메소드
        String selectedChatRoom;
        Scanner sc = new Scanner(System.in);
        System.out.println("아래의 목록에서 채팅방을 선택해주세요.");
        System.out.println(CreatedChatRoom);
        System.out.print("채팅방 선택 : ");
        selectedChatRoom = sc.next();
        sc.close();
        return selectedChatRoom;
    }
   
    public ArrayList<UserData> getUserList(String selectedChatRoom){//선택된 채팅방의 사용자 목록을 반환하는 메소드
        ArrayList<UserData> userDataList = new ArrayList<>();
        String query = new String("SELECT userID, studno, dept, exitDate, isExit, pwd, matchDeptResult, matchStudnoResult, isReport, isLocked FROM user WHERE matchStudnoResult = '" + selectedChatRoom + "' OR matchDeptResult = '" + selectedChatRoom + "';");
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            userData = new UserData();
            String token = st.nextToken();
            userData.userID = token;
            token = st.nextToken();
            userData.studno = token;
            token = st.nextToken();
            userData.dept = token;
            token = st.nextToken();
            userData.exitDate = token;
            boolean bToken = Boolean.valueOf(st.nextToken());
            userData.isExit = bToken;
            token = st.nextToken();
            userData.pwd = token;
            token = st.nextToken();
            userData.matchDeptResult = token;
            token = st.nextToken();
            userData.matchStudnoResult = token;
            bToken = Boolean.valueOf(st.nextToken());
            userData.isReport = bToken;
            bToken = Boolean.valueOf(st.nextToken());
            userData.isLocked = bToken;
            userDataList.add(userData);
        }
        return userDataList;
    }
    
    public UserData selectUserAndExit(){//사용자 목록에서 강퇴할 사용자를 선택하는 메소드
        Scanner sc = new Scanner(System.in);
        System.out.println("아래의 목록에서 강퇴할 사용자를 선택해주세요.");
        Iterator<UserData> it = userDataList.iterator();
        while(it.hasNext()){
            String userID = it.next().userID;
            System.out.print(userID+", ");
        }
        System.out.print("사용자 선택 : ");
        String selectedUser = sc.next();
        it = userDataList.iterator();
        while(it.hasNext()){
            userData = it.next();
            if(userData.userID.equals(selectedUser)){
                break;
            }
        }
        sc.close();
        return userData;
    }
    
    public void saveExitUser(UserData userData){//사용자가 강퇴당한 날짜를 기록하고 강퇴 속성을 true로 설정한 후 DB에 저장하는 메소드
        String query = new String("UPDATE user SET exitDate = SYSDATE, isExit = 'true' WHERE studno = '" + userData.studno + "';");
        db.startQuery(query);
    }
    
     //-----------------------------------U2. 채팅방 관리----------------------------------------------------------------------------
     public void manageChatRoom(){                              
        if(reportContent.isEmpty()){
            System.out.println("신고내용 없음.");
        }
        else{
            CreatedChatRoom=getCreatedChatRoom();               //U2.Sequence 1,2
            SelectedChatRoom = selectCreatedChatRoom();         //U2.Sequence 3,4
            userDataList = getUserList(SelectedChatRoom);       //U2.Sequence 5,6
            saveExitUser(selectUserAndExit());                  //U2.Sequence 7,8
        }
     }
    
    //-----------------------------------U3. 조회----------------------------------------------------------------------------
    public void selectInquery(){                                //U3.Sequence 1
        ArrayList<String> selectedList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("조회 종류를 선택해주세요.\n0 : 채팅방, 1 : 사용자, 2 : 강제 퇴장된 사용자, 3 : 신고 내용");
        System.out.print("조회 종류 선택 : ");
        int inqueryType = sc.nextInt();                         //U3.Sequence 2
        switch(inqueryType){
            case 0 : 
                selectedList = getCreatedChatRoom();            //U3.Sequence 3,4
                break;
            case 1 :
                selectedList = getUserList();                   //U3.Sequence 5,6
                break;
            case 2 :
                selectedList = getExitUser();                   //U3.Sequence 7,8
                break;
            case 3 :
                selectedList = getReportContent();              //U3.Sequence 9,10
                reportContent = selectedList;
                break;
        }
        sortType = selectSortType();                            //U3.Sequence 11,12
        if(sortType == 0){
            sortDictionary(selectedList);                       //U3.Sequence 13
        }
        else if(sortType == 1){
            sortStudno(selectedList);                           //U3.Sequence 14
        }
        System.out.println(selectedList);
        sc.close();
    }

    public ArrayList<String> getUserList(){//전체 사용자 목록을 반환하는 메소드
        ArrayList<String> userList = new ArrayList<>();
        String query = new String("SELECT studno FROM user;");
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            userList.add(token);
        }
        return userList;
    }
    
    public ArrayList<String> getExitUser(){//강퇴된 사용자 목록을 반환하는 메소드
        ArrayList<String> exitList = new ArrayList<>();
        String query = "SELECT studno FROM user WHERE isExit = 'true';";
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            exitList.add(token);
        }
        return exitList;
    }
    
    public ArrayList<String> getReportContent(){//신고 내용 목록을 반환하는 메소드
        ArrayList<String> reportContent = new ArrayList<>();
        String query = "SELECT reportContent FROM report;";
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            reportContent.add(token);
        }
        return reportContent;
    }
    
    public int selectSortType(){//정렬 방식을 선택하는 메소드
        Scanner sc = new Scanner(System.in);
        System.out.println("정렬 방식을 선택해주세요.\n0 : 사전 순, 1 : 학번 순");
        System.out.print("정렬 방식 선택 : ");
        int SortType = sc.nextInt();
        sc.close();
        return SortType;
    }

    public ArrayList<String> sortDictionary(ArrayList<String> chatRoomList){//사전 순으로 정렬하는 메소드
        for(int i = 0; i < chatRoomList.size() - 1; i++){
            for(int j = i+1; j < chatRoomList.size(); j++){
                if(chatRoomList.get(i).compareTo(chatRoomList.get(j)) > 0){
                    String temp = chatRoomList.get(i);
                    chatRoomList.set(i,chatRoomList.get(j));
                    chatRoomList.set(j,temp);
                }
            }
        }
        return chatRoomList;
    }
    
    public ArrayList<String> sortStudno(ArrayList<String> chatRoomList){//학번 순(오름차순)으로 정렬하는 메소드
        Collections.sort(chatRoomList);
        return chatRoomList;
    }
}