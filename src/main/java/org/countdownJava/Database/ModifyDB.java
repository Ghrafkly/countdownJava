package org.countdownJava.Database;

import org.countdownJava.Math.Evaluate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModifyDB {
    private final Connection dbConnection;

    public ModifyDB() {
        DatabaseConnection db = new DatabaseConnection();
        dbConnection = db.connect();
    }

    public void addCP(Map<List<String>, List<List<String>>> cpMap) {
        PreparedStatement pstmt;
        int count = 1;
        List<String> colNames = new ArrayList<>();

        for (Map.Entry<List<String>, List<List<String>>> entry : cpMap.entrySet()) {
            System.out.println(entry.getValue().size());
//            for (List<String> permutation : entry.getValue()) {
//                colNames.add("perms" + count);
//                count++;
//            }
        }
//
//        try {
//
//
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    public static void main(String[] args) {
        ModifyDB db = new ModifyDB();
    }

    public void resetCP() {
        CallableStatement cstmt;
        try {
            cstmt = dbConnection.prepareCall("call resetCP()");
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countColumnsCP() {
        PreparedStatement pstmt;
        int columns;

        try {
            pstmt = dbConnection.prepareStatement("""
                        select count(COLUMN_NAME)
                        from information_schema.columns
                        where table_schema = 'public'
                          and table_name = 'combinations_permutations'
                        group by COLUMN_NAME;
                        """);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            columns = rs.getInt("count");
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Number of columns in combinations_permutations_big: " + columns);
        return columns;
    }

    public void addColumnsCP() {
        CallableStatement stmt;

        try {
            stmt = dbConnection.prepareCall("call addColumnsCP(?)");
            stmt.setString(1, "perm");
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
