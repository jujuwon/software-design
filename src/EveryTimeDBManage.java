public class EveryTimeDBManage {
    public String startQuery(String query){     //ref2 - 1 startQuery메소드 
        boolean connectResult = ETDBconnect();  //ref2 - 2,3 채팅방DB 연결 및 반환
        if(connectResult == false){             //ref2 - EveryTimeSystem연결에 실패한 경우
            return "error";                     //ref2 - 4 에러 반환
        }
        else {                                  //ref2 - EveryTimeSystem연결에 성공한 경우
            String data = runQuery(query);      //ref2 - 5,6 runQuery 실행 및 반환
            ETDBclose();                        //ref2 - 7 DB연결 닫기
            return data;                        //ref2 - 추가 runQuery에서 얻은 data 반환값
        }
    }




    public boolean ETDBconnect(){
        //EveryTimeSystem 연결 시도
        return true;
    }
    public String runQuery(String query){
        String data = null;
        //EveryTimeSystem에 Query를 날려 해당하는 데이터 가져오기
        return data;
    }
    public void ETDBclose(){
        //EveryTimeSystem 연결 끊기
    }
}