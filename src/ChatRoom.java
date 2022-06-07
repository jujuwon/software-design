import java.util.*;

public class ChatRoom {
    String keyword;
    ArrayList<String> chatContent;
    ArrayList<String> searchResult;
    String reportReason;
    UserData userData;
    DBManage db = new DBManage();
    
    //-----------------------------------------U6. 대화 내용 검색----------------------------------------------------------------------
    public void SearchChat(){//대화 내용 검색
        keyword = inputKeyword();                                           //U6.Sequence 1,2 검색할 키워드 요청 및 반환
        chatContent = getChatContent();                                     //U6.Sequence 3,4,5 채팅방의 대화내역을 요청 및 반환
        searchResult = searchResult(keyword);                               //U6.Sequence 6,7 대화 내역에서 키워드를 탐색 후 반환
        if(searchResult.isEmpty()){                                         //U6.Sequence 탐색 결과가 없을 시
            showError();                                                    //U6.Sequence 8 오류 표시
        }
        else{                                                               //U6.Sequence 탐색 결과가 존재할 시
            System.out.println("검색된 결과 개수 : " + searchResult.size()); //U6.Sequence 9 검색된 결과 개수 출력
            //글자 하이라이트                                                //U6.Sequence 10 검색 결과에 강조 표시
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

    public ArrayList<String> getChatContent(){//채팅방에서 대화 내역을 가져오는 메소드
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

    public ArrayList<String> searchResult(String keyword){//대화 내역에서 키워드를 찾는 메소드
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

    public String inputReportReason(){//신고사유를 입력하는 함수
        String reportReason;
        Scanner sc = new Scanner(System.in);
        System.out.println("신고 사유를 입력해주세요.");
        System.out.print("신고 사유 입력 : ");
        reportReason = sc.nextLine();
        sc.close();
        return reportReason;
    }

    //-----------------------------------------U7. 사용자 신고----------------------------------------------------------------------
    public void userReport(){//사용자 신고
        userData = new UserData();
        chatContent = getChatContent();
        String chat = selectChatContent(chatContent);               //U7.Sequence 1 대화 내역에서 채팅 선택
        StringTokenizer st = new StringTokenizer(chat, ":");
        userData.userID = st.nextToken();
        reportReason = inputReportReason();                         //U7.Sequence 2,3 신고 사유 작성 후 반환
        selectReport();                                             //U7.Sequence 4 신고를 선택
    }

    public void selectReport(){//신고당한 사용자 이름 옆에 주의 표시를 하고 신고사유를 DB에 저장하는 함수
        if(reportReason!=null){                                     //U7.Sequence 신고 사유가 없을 시
            showWarning(userData);                                  //U7.Sequence 5 신고 당한 사용자의 이름 옆에 주의 표시
            saveReportContent(reportReason);                        //U7.Sequence 6 신고 사유를 DB에 저장
        }
        else{
            showError();                                            //U7.Sequence 7 오류 표시
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

    public void showWarning(UserData userData){//사용자 이름 옆에 주의 표시를 하는 함수
        //이름 옆에 주의 표시를 해줌
    }

    public void saveReportContent(String reportReason){//신고사유를 DB에 저장하는 함수
        String query = "INSERT INTO reportContent VALUES ('" + reportReason + "')";
        db.startQuery(query);
    }
}
