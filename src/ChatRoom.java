import java.util.*;

public class ChatRoom {
    String keyword;
    ArrayList<String> chatContent;
    ArrayList<String> searchResult;
    String reportReason;
    UserData userData;
    DBManage db = new DBManage();
    
    //-----------------------------------------U6. 대화 내용 검색----------------------------------------------------------------------
    public void SearchChat(){
        keyword = inputKeyword();                                           //U6.Sequence 1,2
        chatContent = getChatContent();                                     //U6.Sequence 3,4,5
        searchResult = searchResult(keyword);                               //U6.Sequence 6,7
        if(searchResult.isEmpty()){
            showError();                                                    //U6.Sequence 8
        }
        else{
            System.out.println("검색된 결과 개수 : " + searchResult.size()); //U6.Sequence 9
            //글자 하이라이트                                                //U6.Sequence 10
        }
    }

    public String inputKeyword(){//키워드를 입력받는 메소드
        String keyword;
        Scanner sc = new Scanner(System.in);
        System.out.println("키워드를 입력해주세요.");
        System.out.print("키워드 입력 : ");
        keyword = sc.next();
        sc.close();
        return keyword;
    }

    public ArrayList<String> getChatContent(){//채팅방에서 대화내역을 가져오는 메소드
        ArrayList<String> chatContent = new ArrayList<>();
        String query = "SELECT chatContent FROM chat;";
        String data = db.startQuery(query);
        StringTokenizer st = new StringTokenizer(data, ",");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            chatContent.add(token);
        }
        return chatContent;
    }

    public ArrayList<String> searchResult(String keyword){//대화내역에서 키워드를 찾는 메소드
        ArrayList<String> searchResult = new ArrayList<String>();
        Iterator<String> it = chatContent.iterator();        
        while (it.hasNext()) {            
            String str = it.next();            
            if (str.contains(keyword)) {                
                searchResult.add(str);          
            }        
        }
        return searchResult;
    }

    public void showError(){//에러를 보여주는 메소드
        System.out.println("에러 : 신고사유가 없습니다.");
    }

    //-----------------------------------------U7. 사용자 신고----------------------------------------------------------------------
    public void userReport(){
        userData = new UserData();
        chatContent = getChatContent();
        String chat = selectChatContent(chatContent);               //U7.Sequence 1
        StringTokenizer st = new StringTokenizer(chat, ":");
        userData.userID = st.nextToken();
        reportReason = inputReportReason();                         //U7.Sequence 2,3
        selectReport();                                             //U7.Sequence 4
    }

    public void selectReport(){//신고당한 사용자 이름 옆에 주의 표시를 하고 신고사유를 DB에 저장하는 함수
        if(!(reportReason==null)){
            showWarning(userData);                                  //U7.Sequence 5
            saveReportContent(reportReason);                        //U7.Sequence 6
        }
        else{
            showError();                                            //U7.Sequence 7
        }
    }

    public String selectChatContent(ArrayList<String> chatContent){//채팅내역에서 하나의 채팅을 선택하는 메소드
        Scanner sc = new Scanner(System.in);
        System.out.println("대화 내용을 선택해주세요.");
        System.out.println(chatContent);
        System.out.println("대화 선택");
        int selectedNum = sc.nextInt();
        sc.close();
        return chatContent.get(selectedNum);
    }

    public String inputReportReason(){//신고사유를 입력하는 함수
        String reportReason;
        Scanner sc = new Scanner(System.in);
        System.out.println("신고 사유를 입력해주세요.");
        System.out.print("신고 사유 입력 : ");
        reportReason = sc.nextLine();
        sc.close();
        return reportReason;
    }

    public void showWarning(UserData userData){//사용자 이름 옆에 주의 표시를 하는 함수
        //이름 옆에 주의 표시를 해줌
    }

    public void saveReportContent(String reportReason){//신고사유를 DB에 저장하는 함수
        String query = "INSERT INTO reportContent VALUES ('" + reportReason + "')";
        db.startQuery(query);
    }
}
