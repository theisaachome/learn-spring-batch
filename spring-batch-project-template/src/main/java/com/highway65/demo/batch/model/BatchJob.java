package com.highway65.demo.batch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
public class BatchJob {
    private  long id;
    private  String name;
    private  String status;

    public  BatchJob(ResultSet rs)throws SQLException{
        id=rs.getLong("JOB_EXECUTION_ID");
        name=rs.getString("JOB_NAME");
        status=rs.getString("STATUS");
    }
}
