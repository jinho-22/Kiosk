package a_010_java_after2;

import java.sql.*;
import java.sql.Date;
import java.util.*;

class Product_SalesList{
	public Date      tot_system_date;                //상품코드
    public int 	ord_buying_count;           //상품명
    public int     	ord_price;        	//수량
    public int    	ord_pdt_id;      //금액
    public String 		pdt_id_name;
    
    public int cnt;

	void printScore() {
        System.out.printf(" %3d  %10s  %3d  %9d  %5d %10s \n",
                cnt,tot_system_date, ord_buying_count, ord_price, ord_pdt_id, pdt_id_name); //프린트
    }
	
	
}
public class Kiosk_Product_SalesList {
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
	            	sql="select to_date(to_char(tot_system_date, 'yyyy-mm-dd'),'yyyy-mm-dd') tot_system_date , ord_buying_count, ord_price, ord_pdt_id, pdt_id_name "
	            			+ "from tbl_product_master a,  tbl_order_list b, tbl_order_total c "
	            			+ "where a.pdt_id = b.ord_pdt_id and b.ord_no = c.tot_ord_no "
	            			+ "order by ord_pdt_id, tot_system_date";
	            }else if(in != 1 && in!=9) {
	            	//전제 검색
	            	sql=" select to_date(to_char(tot_system_date, 'yyyy-mm-dd'),'yyyy-mm-dd') tot_system_date , ord_buying_count, ord_price, ord_pdt_id, pdt_id_name "
	            			+ " from tbl_product_master a,  tbl_order_list b, tbl_order_total c "
	            			+ " where a.pdt_id = b.ord_pdt_id and b.ord_no = c.tot_ord_no and ord_pdt_id="+ in
	            			+ " order by ord_pdt_id, tot_system_date";
	            }
	            System.out.println("========================================================");
	            System.out.println(" 순번      판매일자    수량     금액    상품코드      상품명 ");
	            System.out.println("========================================================");
	            pstmt = conn.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            
	            num_count = 0;
	            
	            Product_SalesList p1 =new Product_SalesList();
	            while(rs.next()) {
	                p1.cnt = num_count+1;
	                num_count++;
	                p1.tot_system_date = rs.getDate("tot_system_date");
	                p1.ord_buying_count = rs.getInt("ord_buying_count");
	                p1.ord_price = rs.getInt("ord_price");
	                p1.ord_pdt_id = rs.getInt("ord_pdt_id");
	                p1.pdt_id_name = rs.getString("pdt_id_name");
	                p1.printScore(); //출력
	               
	            }
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	     
        }while(true);
	}
}