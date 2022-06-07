import java.util.*;
public class Administrator {
    DBManage db = new DBManage();
    int roomType;
    ArrayList<String> unCreatedDeptChatRoomList;
    String dept;
    ArrayList<String> unCreatedStudnoChatRoomList;
    String Studno;
    ArrayList<String> reportContent;//getReportContent가 신고 내역 목록을 반환하므로 String -> ArrayList<String>
    ArrayList<String> CreatedChatRoom;
    ArrayList<String> userList;
    String SelectedChatRoom;
    UserData userData;
    ArrayList<UserData> userDataList;//selectUserAndExit()는 유저 데이터 리스트에서 강퇴할 사용자를 선택해야 되는데 유저 데이터 리스트를 넘겨줄 파라미터가 존재하지 않으므로 새로 생성
    int inqueryType;
    int sortType;

    //-----------------------------------------U1. 채팅방 생성----------------------------------------------------------------------
    public void createChatRoom(){//채팅방 생성                              //U1.Sequence 1 관리자가 채팅방 생성 선택
        Scanner sc = new Scanner(System.in);
        System.out.println("생성할 채팅방의 종류를 선택해주세요.\n0 : 학과 채팅방, 1 : 학과 채팅방");
        System.out.print("채팅방 종류 입력 : ");
        roomType = sc.nextInt();                                            //U1.Sequence 2 채팅방 종류 선택 및 반환
        if(roomType == 0){                                                  //U1.Sequence 학과 채팅방 생성 선택 시
            unCreatedDeptChatRoomList = getUnCreatedDeptChatRoomList();     //U1.Sequence 3,4 생성되지 않은 학과 채팅방 목록을 요청 및 반환
            dept = selectDept();                                            //U1.Sequence 5,6 학과 선택 및 반환
            saveCreatedDeptChatRoom(dept);                                  //U1.Sequence 7 선택한 학과에 해당하는 채팅방을 DB에 저장
        }
        else if(roomType == 1){                                             //U1.Sequence 학번 채팅방 생성 선택 시
            unCreatedStudnoChatRoomList = getUnCreatedStudnoChatRoomList(); //U1.Sequence 8,9 생성되지 않은 학번 채팅방 목록을 요청 및 반환
            Studno = selectStudno();                                        //U1.Sequence 10,11 학번 선택 및 반환
            saveCreatedStudnoChatRoom(Studno);                              //U1.Sequence 12 선택한 학번에 해당하는 채팅방을 DB에 저장
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
    
    public void saveCreatedDeptChatRoom(String dept){//입력된 학과에 해당하는 채팅방을 DB에 기록
        String query = new String("UPDATE chatRoom SET isCreated = 1 WHERE name = LIKE '%" + dept + "';");
        db.startQuery(query);
    }

    public ArrayList<String> getUnCreatedStudnoChatRoomList(){//생성되지 않은 학번 채팅방 목록을 반환하는 메소드
        ArrayList<String> unCreatedStudnoChatRoomList = new ArrayList<>();
        String query = new String("SELECT name FROM chatRoom WHERE isCreated = 0 AND name = LIKE 'studno%';");
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
    
    public void saveCreatedStudnoChatRoom(String Studno){//입력된 학번에 해당하는 채팅방을 DB에 기록
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
     public void manageChatRoom(){//채팅방 관리                       
        if(reportContent.isEmpty()){                            //U2.Sequence 신고내용이 없을 시
            System.out.println("신고내용 없음.");
        }
        else{                                                   //U2.Sequence 신고내용이 있을 시
            CreatedChatRoom = getCreatedChatRoom();             //U2.Sequence 1,2 채팅방 목록 요청 및 반환
            SelectedChatRoom = selectCreatedChatRoom();         //U2.Sequence 3,4 채팅방 목록 중에서 하나를 선택
            userDataList = getUserList(SelectedChatRoom);       //U2.Sequence 5,6 선택된 채팅방의 사용자 목록을 요청 및 반환
            userData = selectUserAndExit();                     //U2.Sequence 7 사용자 목록에서 강제 퇴장할 사용자를 선택
            saveExitUser(userData);                             //U2.Sequence 8 선택한 사용자를 강제 퇴장하고 DB에 저장
        }
     }
    
    //-----------------------------------U3. 조회----------------------------------------------------------------------------
    public void selectInquery(){//조회                          //U3.Sequence 1 관리자가 조회 선택
        ArrayList<String> selectedList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("조회 종류를 선택해주세요.\n0 : 채팅방, 1 : 사용자, 2 : 강제 퇴장된 사용자, 3 : 신고 내용");
        System.out.print("조회 종류 선택 : ");
        int inqueryType = sc.nextInt();                         //U3.Sequence 2 조회 종류를 선택 및 반환
        switch(inqueryType){
            case 0 :                                            //U3.Sequence 조회 종류가 채팅방 목록 시
                selectedList = getCreatedChatRoom();            //U3.Sequence 3,4 채팅방 목록 요청 및 반환
                break;
            case 1 :                                            //U3.Sequence 조회 종류가 사용자 목록 시
                selectedList = getUserList();                   //U3.Sequence 5,6 사용자 목록 요청 및 반환
                break;
            case 2 :                                            //U3.Sequence 조회 종류가 강제 퇴장된 사용자 목록 시
                selectedList = getExitUser();                   //U3.Sequence 7,8 강제 퇴장된 사용자 목록 요청 및 반환
                break;
            case 3 :                                            //U3.Sequence 조회 종류가 신고내역 목록 시
                selectedList = getReportContent();              //U3.Sequence 9,10 신고내역 목록 요청 및 반환
                reportContent = selectedList;
                break;
        }
        sortType = selectSortType();                            //U3.Sequence 11,12 정렬 방식을 선택 및 반환
        if(sortType == 0){                                      //U3.Sequence 정렬 방식이 사전 순일 시
            sortDictionary(selectedList);                       //U3.Sequence 13 사전 순으로 리스트 정렬
        }
        else if(sortType == 1){                                 //U3.Sequence 정렬 방식이 학번 순(오름차순)일 시
            sortStudno(selectedList);                           //U3.Sequence 14 학번 순(오름차순)으로 리스트 정렬
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