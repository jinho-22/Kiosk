package a_010_java_after2;

import java.sql.*;
import java.sql.Date;
import java.util.*;

class Product_SalesListToatl{
	public int      ord_pdt_id;                //상품코드
    public String  pdt_id_name;           //상품명
    public int     	ord_buying_count;        	//수량
    public int    	ord_price;      //금액
    
    public int cnt;
	public Date tot_system_date;

	void printScore() {
        System.out.printf(" %3d  %5d  %3d  %9d   %10s \n",
                cnt,ord_pdt_id, ord_buying_count, ord_price, pdt_id_name); //프린트
    }
	
	
}
public class Kiosk_Product_SalesListToatl {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        int num_count =0;
        
        
        do {
        int in=0; //입력받을 변수값 선언

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
	            
	            sql="select count(*) from tbl_product_master";
	            
	            pstmt = conn.prepareStatement(sql);
	            ResultSet rs = pstmt.executeQuery();
	            rs.next();
	            num_count = rs.getInt(1);
	            System.out.print("상품코드를 입력하세요. 상품별 리스트를 출력합니다. 전체:1 종료:9   ");
	            in = input.nextInt(); //상품코드 받아오기
	            if(in==9) {
	            	Kiosk_MainMenu.main(args); //종료
	            }else if(in == 1) {
	            	//전제 검색
	            	sql="select ord_pdt_id, sum(ord_buying_count) ord_buying_count, sum(ord_price) ord_price, pdt_id_name "
	            		+ "from  tbl_product_master a,  tbl_order_list b, tbl_order_total c "
	            		+ "where a.pdt_id = b.ord_pdt_id and b.ord_no = c. tot_ord_no "
	            		+ "group by pdt_id_name, ord_pdt_id";
	            }else if(in!=0 && in!=1) {
	            	//설정한 번호 검색
	                sql="select ord_pdt_id, sum(ord_buying_count) ord_buying_count, sum(ord_price) ord_price, pdt_id_name "
		            		+ "from  tbl_product_master a,  tbl_order_list b, tbl_order_total c "
		            		+ "where a.pdt_id = b.ord_pdt_id and b.ord_no = c. tot_ord_no and ord_pdt_id = " + in
		            		+ "group by pdt_id_name, ord_pdt_id";
	            }
	            System.out.println("============================================");
	            System.out.println(" 순번  상품코드  수량       금액           상품명 ");
	            System.out.println("============================================");
	            pstmt = conn.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            
	            num_count = 0;
	            
	            Product_SalesListToatl p1 =new Product_SalesListToatl();
	            while(rs.next()) {
	                p1.cnt = num_count+1;
	                num_count++;
	                p1.ord_pdt_id = rs.getInt("ord_pdt_id");
	                p1.ord_buying_count = rs.getInt("ord_buying_count");
	                p1.ord_price = rs.getInt("ord_price");
	                p1.pdt_id_name = rs.getString("pdt_id_name");
	                
	                p1.printScore(); //출력
	               
	            }
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	     
        }while(true);
	}
}