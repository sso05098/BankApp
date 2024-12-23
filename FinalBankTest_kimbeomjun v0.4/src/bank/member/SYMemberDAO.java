//package bank.member;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import bank.account.AccountVO;
//import bank.common.DBConnectionFactory;
//
//public class SYMemberDAO implements MemberDAO {
//
//	@Override
//	public int insertMember(MemberVO member) {
//		int result = 0;
//		// sql문 
//		String sql = new StringBuilder()
//		.append("insert into FBANK_MEMBER (no, id, pwd, name, register_time) ")
//		.append("values (?, ?, ?, ?, sysdate)").toString();
//		
//		// DB 연결
//		try (
//				// db 연결
//				Connection conn = DBConnectionFactory.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//			) {
//				pstmt.setString(1, member.getId());
//				pstmt.setString(2,  member.getPwd());
//				pstmt.setString(3, member.getName());
//				
//				result = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 결과 확인
//		return result;
//	}
//
//	@Override
//	public MemberVO selectMember(String loginId, String loginPwd) {
//		MemberVO member = null;
//		ResultSet rs = null;
//		
//		String sql = "select id, pwd, name, to_char(register_time, 'yyyy-mm-dd') as register_time from FBANK_MEMBER where id = ? and pwd = ?";
//		
//		try (
//				Connection conn = DBConnectionFactory.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//			) {
//				pstmt.setString(1, loginId);
//				pstmt.setString(2, loginPwd);
//				rs = pstmt.executeQuery();
//				
//				if(rs.next()) {
//					String member_nm = rs.getString("name");
//					Date register_time = rs.getDate("register_time");
//					
//					member = new MemberVO(loginId, loginPwd, member_nm, new Date());
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return member;
//	}
//	
//
//}
