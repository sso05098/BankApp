package bank.account;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bank.common.DBConnectionFactory;

public class OracleAccountDAO implements AccountDAO {

    @Override
    public int insert(String member_id, int add_money) {
        int result = 0;
        String sql = "INSERT INTO FBANK_ACCOUNT (NO, MEMBER_NO, BALANCE, REGDATE) VALUES (FBANK_ACCOUNT_SEQ.NEXTVAL, ?, ?, SYSDATE)";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            int memberNo = getMemberNo(member_id);
            if (memberNo == 0) {
                System.out.println("회원이 존재하지 않습니다. 계좌 개설을 진행할 수 없습니다.");
                return result; // 회원이 없으므로 0 반환
            }
            pstmt.setInt(1, memberNo);
            pstmt.setInt(2, add_money);
            result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("계좌가 개설되었습니다. [MEMBER_NO=" + memberNo + ", BALANCE=" + add_money + "]");
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return result;
    }

    private int getMemberNo(String member_id) {
        int memberNo = 0;
        String sql = "SELECT NO FROM FBANK_MEMBER WHERE ID = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, member_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                memberNo = rs.getInt("NO");
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return memberNo;
    }

    @Override
    public AccountVO select(int account_no) {
        AccountVO account = null;
        String sql = "SELECT * FROM FBANK_ACCOUNT WHERE NO = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, account_no);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int member_no = rs.getInt("MEMBER_NO");
                int account_money = rs.getInt("BALANCE");
                Date register_time = rs.getDate("REGDATE");
                account = new AccountVO(account_no, String.valueOf(member_no), account_money, register_time);
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return account;
    }

    @Override
    public List<AccountVO> selectAll(String member_id) {
        List<AccountVO> accountList = new ArrayList<>();
        String sql = "SELECT * FROM FBANK_ACCOUNT WHERE MEMBER_NO = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            int memberNo = getMemberNo(member_id);
            pstmt.setInt(1, memberNo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int account_no = rs.getInt("NO");
                int account_money = rs.getInt("BALANCE");
                Date register_time = rs.getDate("REGDATE");
                accountList.add(new AccountVO(account_no, member_id, account_money, register_time));
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return accountList;
    }

    @Override
    public int updateBalance(int account_no, int account_money) {
        int result = 0;
        String sql = "UPDATE FBANK_ACCOUNT SET BALANCE = ? WHERE NO = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, account_money);
            pstmt.setInt(2, account_no);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int delete(int account_no) {
        int result = 0;
        String sql = "DELETE FROM FBANK_ACCOUNT WHERE NO = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, account_no);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int insertHistory(AccountHistoryVO accHistory) {
        int result = 0;
        String sql = "INSERT INTO FBANK_HISTORY (NO, ACCOUNT_NO, TASK, MONEY, BALANCE, TASK_TIME) VALUES (FBANK_HISTORY_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, accHistory.getAccount_no());
            pstmt.setString(2, accHistory.getTask());
            pstmt.setInt(3, accHistory.getTask_money());
            pstmt.setInt(4, accHistory.getAccount_money());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return result;
    }

    @Override
    public AccountVO selectAccById(String member_id) {
        AccountVO account = null;
        String sql = "SELECT * FROM (SELECT * FROM FBANK_ACCOUNT WHERE MEMBER_NO = ? ORDER BY REGDATE DESC) WHERE ROWNUM = 1";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            int memberNo = getMemberNo(member_id);
            pstmt.setInt(1, memberNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int account_no = rs.getInt("NO");
                int account_money = rs.getInt("BALANCE");
                Date register_time = rs.getDate("REGDATE");
                account = new AccountVO(account_no, member_id, account_money, register_time);
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return account;
    }

    @Override
    public List<AccountHistoryVO> selectHistoryByAcc(List<AccountVO> accountList) {
        List<AccountHistoryVO> historyList = new ArrayList<>();
        String sql = "SELECT HISTORY_NO, TASK, TASK_MONEY, BALANCE, TO_CHAR(TASK_TIME, 'yyyy-mm-dd') AS TASK_TIME FROM FBANK_HISTORY WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            for (AccountVO account : accountList) {
                pstmt.setInt(1, account.getAccount_no());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int history_no = rs.getInt("HISTORY_NO");
                    String task = rs.getString("TASK");
                    int task_money = rs.getInt("TASK_MONEY");
                    int account_money = rs.getInt("BALANCE");
                    String task_time = rs.getString("TASK_TIME");
                    historyList.add(new AccountHistoryVO(account.getAccount_no(), history_no, task, task_money, account_money, task_time));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL 오류: " + e.getMessage());
        }
        return historyList;
    }
}
