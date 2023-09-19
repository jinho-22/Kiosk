package a_010_java_after2;

import java.sql.*;
import java.util.*;
import java.util.Date;

class Product_BuyList{
    public int     ord_no;                //주문번호
    public int     ord_count;             //count
    public int     ord_pdt_id;            //상품코드
    public int     ord_buying_count;      //주문수량
    public int     ord_pdt_unit_price;    //상품단가
    public int	   ord_price;             //금액

    public int     cnt;                   //순서
    public String  method;


   
    void printScore() {
        System.out.printf("%5d   %3d    %5d %3d   %7d %9d \n",
        		ord_no,ord_count,ord_pdt_id,ord_buying_count,ord_pdt_unit_price,ord_price);
    }
}
public class Kiosk_Product_BuyList {

    public static void main(String[] args) {
        int tot_price = 0; 
        Scanner input = new Scanner(System.in);
       
        
        
        int num_count =0;
        do {
        int in=0;
        
       
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql;
       
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("클래스 로딩 성공");
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("DB 접속");
            sql="select count(*) num from tbl_order_list";
            if(in > 0 && in <=3) {
                sql="select count(*) num from tbl_order_list where tot_buying_method=" + in;
               
            }
            System.out.println("주문번호를 입력하세요. 주문 리스트를 출력합니다.  전체:1  종료:0");
            in = input.nextInt();
            
            if(in==0) {
            	break;
            } else if(in==1) {
            	pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                num_count = rs.getInt("num");
               
                System.out.println("==============================================");
                System.out.println("주문번호   순번  상품번호  수량 	단가      금액");
                System.out.println("==============================================");
               
                if(in > 0 && in <= 3) {
                    sql="select * from tbl_order_List order by ord_count";
                }else {
                    sql= "select * from tbl_order_List order by ord_no, ord_count";
                }
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
               
                num_count = 0;
                
                Product_BuyList p =new Product_BuyList();
                while(rs.next()) {
                    p.cnt = num_count+1;
                    num_count++;
                    p.ord_no = rs.getInt("ord_no");
                    p.ord_count = rs.getInt("ord_count");
                    p.ord_pdt_id = rs.getInt("ord_pdt_id");
                    p.ord_buying_count = rs.getInt("ord_buying_count");
                    p.ord_pdt_unit_price = rs.getInt("ord_pdt_unit_price");
                    p.ord_price = rs.getInt("ord_price");
                    tot_price += rs.getInt("ord_price");
                    p.printScore();
            }
                System.out.println("==============================================");
                System.out.println("***매출합계:"+tot_price);
                Kiosk_Product_BuyList.main(args);
        }else {
        	pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            num_count = rs.getInt("num");
           
            System.out.println("==============================================");
            System.out.println("주문번호   순번  상품번호  수량 	단가      금액");
            System.out.println("==============================================");
           
            if(in > 0 && in <= 3) {
                sql="select * from tbl_order_List where  ord_no=" + in +"order by ord_count";
            }else {
                sql= "select * from tbl_order_List where ord_no="+in+" order by ord_no, ord_count";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
           
            num_count = 0;
            
            Product_BuyList p =new Product_BuyList();
            while(rs.next()) {
                p.cnt = num_count+1;
                num_count++;
                p.ord_no = rs.getInt("ord_no");
                p.ord_count = rs.getInt("ord_count");
                p.ord_pdt_id = rs.getInt("ord_pdt_id");
                p.ord_buying_count = rs.getInt("ord_buying_count");
                p.ord_pdt_unit_price = rs.getInt("ord_pdt_unit_price");
                p.ord_price = rs.getInt("ord_price");
                tot_price += rs.getInt("ord_price");
                p.printScore();
                
        }
            System.out.println("==============================================");
            System.out.println("***주문합계:"+tot_price);
            Kiosk_Product_BuyList.main(args);
    }
       
        
    }catch(Exception e) {
        e.printStackTrace();
    }
        Kiosk_MainMenu.main(args);
        }while(true);
   }
}