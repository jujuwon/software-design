public class DBManage {
    public String startQuery(String query){   //ref1 - 1 startQuery메소드
        boolean connectResult = DBconnect();  //ref1 - 2,3 채팅방DB 연결 및 반환
        if(connectResult == false){           //ref1 - DB연결에 실패한 경우
            return "error";                   //ref1 - 4 에러 반환
        }
        else {                                //ref1 - DB연결에 성공한 경우
            String data = runQuery(query);    //ref1 - 5,6 runQuery 실행 및 반환
            DBclose();                        //ref1 - 7 DB연결 닫기
            return data;                      //ref1 - 추가 runQuery에서 얻은 data 반환값
        }
    }






    public boolean DBconnect(){
        //DB 연결 시도
        return true;
    }
    public String runQuery(String query){
        String data = null;
        //DB에 Query를 날려 해당하는 데이터 가져오기
        return data;
    }
    public void DBclose(){
        //DB 연결 끊기
    }
}
