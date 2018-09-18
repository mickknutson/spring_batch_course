package io.baselogic.batch.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Common DAO for accessing the Spring Batch Database
 */
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class BatchDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String LINE = "+" + new String(new char[55]).replace('\0', '-') + "+";

    private static final String ROW_FORMAT = "|%s|";
    private static final String ROW_TWO_FORMAT = "|%s|%s|";
    private static final String COLUMN_FORMAT = "| %1$-20s ";
    private static final String RESULT_FORMAT = "| %1$-30s |";


    private static final String JOB_EXECUTION_ID = "JOB_EXECUTION_ID";
    private static final String JOB_INSTANCE_ID = "JOB_INSTANCE_ID";
    private static final String JOB_NAME = "JOB_NAME";


    private static final String STEP_EXECUTION_ID = "STEP_EXECUTION_ID";
    private static final String STEP_NAME = "STEP_NAME";


    private static final String START_TIME = "START_TIME";
    private static final String END_TIME = "END_TIME";
    private static final String STATUS = "STATUS";
    private static final String EXIT_CODE = "EXIT_CODE";
    private static final String EXIT_STATUS = "EXIT_STATUS";
    private static final String EXIT_MESSAGE = "EXIT_MESSAGE";
    private static final String JOB_EXECUTION_PARAMS = "BATCH_JOB_EXECUTION_PARAMS";

    private static final String COMMIT_COUNT = "COMMIT_COUNT";
    private static final String READ_COUNT = "READ_COUNT";
    private static final String WRITE_COUNT = "WRITE_COUNT";
    private static final String LAST_UPDATED = "LAST_UPDATED";


    private static final String COLUMN = "COLUMN";
    private static final String RESULT = "RESULT";





    private static final String JOB_EXECUTIONS = "SELECT " +
            "je.JOB_EXECUTION_ID, je.JOB_INSTANCE_ID, ji.JOB_NAME, je.START_TIME, je.END_TIME, je.STATUS, je.EXIT_CODE, je.EXIT_MESSAGE, bjep.* " +
            "FROM BATCH_JOB_EXECUTION je inner join BATCH_JOB_INSTANCE ji inner join BATCH_JOB_EXECUTION_PARAMS bjep " +
            "where je.JOB_INSTANCE_ID=ji.JOB_INSTANCE_ID and je.JOB_EXECUTION_ID = bjep.JOB_EXECUTION_ID order by je.JOB_EXECUTION_ID desc";


    private static final String STEP_EXECUTIONS = "SELECT " +
            "bse.STEP_EXECUTION_ID,bse.JOB_EXECUTION_ID,ji.JOB_NAME, bse.STEP_NAME, bse.START_TIME, bse.END_TIME, bse.COMMIT_COUNT, " +
            "bse.READ_COUNT, bse.WRITE_COUNT, bse.STATUS, bse.EXIT_MESSAGE, bse.LAST_UPDATED " +
            "FROM BATCH_STEP_EXECUTION bse inner join BATCH_JOB_INSTANCE ji inner join BATCH_JOB_EXECUTION je " +
            "where ji.JOB_INSTANCE_ID = je.JOB_INSTANCE_ID and bse.JOB_EXECUTION_ID = je.JOB_EXECUTION_ID order by bse.job_execution_id desc";

    //---------------------------------------------------------------------------//
    // Job Execution

    /**
     * Get Job Executions for debugging
     *
     *
     * @return String formatted table of current Job Executions in the database
     */
    public String logJobExecutions() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n").append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center("JOB EXECUTIONS (DB)", 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_TWO_FORMAT, StringUtils.center(COLUMN, 22), StringUtils.center(RESULT, 32))).append("\n");
        sb.append(LINE).append("\n");

        return jdbcTemplate.query(JOB_EXECUTIONS, rs -> {
            while (rs.next()) {

                sb.append( printRow(JOB_EXECUTION_ID, rs.getString(1)));
                sb.append( printRow(JOB_INSTANCE_ID, rs.getString(2)));

                sb.append( printRow(JOB_NAME, rs.getString(3)));
                sb.append( printRow(START_TIME, rs.getString(4)));
                sb.append( printRow(END_TIME, rs.getString(5)));
                sb.append( printRow(STATUS, rs.getString(6)));
                sb.append( printRow(EXIT_CODE, rs.getString(7)));
                sb.append( printRow(EXIT_CODE, rs.getString(8)));
                sb.append( printRow(JOB_EXECUTION_PARAMS, rs.getString(9)));

                sb.append(LINE).append("\n");
            }

            sb.append("\n");
            return sb.toString();
        });
    }

    public int countJobExecutions() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM BATCH_JOB_EXECUTION", Integer.class);
    }

    public int countJobInstances() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM BATCH_JOB_INSTANCE", Integer.class);
    }



    public String logJobExecutions(JobExecution jobExecution) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n").append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center("JOB EXECUTION", 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_TWO_FORMAT, StringUtils.center(COLUMN, 22), StringUtils.center(RESULT, 32))).append("\n");
        sb.append(LINE).append("\n");

        sb.append( printRow(JOB_EXECUTION_ID, ""+jobExecution.getJobId()) );
        sb.append( printRow(JOB_INSTANCE_ID, ""+jobExecution.getJobInstance().getInstanceId()) );

        sb.append( printRow(JOB_NAME, ""+jobExecution.getJobConfigurationName()));
        sb.append( printRow(START_TIME, ""+jobExecution.getStartTime()));
        sb.append( printRow(END_TIME, ""+jobExecution.getEndTime()));
        sb.append( printRow(STATUS, ""+jobExecution.getStatus()));
        sb.append( printRow(EXIT_CODE, ""+jobExecution.getExitStatus().getExitCode()));
        sb.append( printRow(EXIT_CODE, ""+jobExecution.getExitStatus().getExitDescription()));

        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(JOB_EXECUTION_PARAMS, 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(""+jobExecution.getJobParameters(), 55))).append("\n");

        sb.append(LINE).append("\n");
        sb.append("\n\n");

        return sb.toString();
    }


    //---------------------------------------------------------------------------//
    // Steps Execution

    /**
     * Get Step Executions for debugging
     *
     * TODO: Need to add the following columns:
     *
     sb.append("rollbackCount").append(stepExecution.getRollbackCount()).append("\n");
     sb.append("readSkipCount").append(stepExecution.getReadSkipCount()).append("\n");
     sb.append("processSkipCount").append(stepExecution.getProcessSkipCount()).append("\n");
     sb.append("writeSkipCount").append(stepExecution.getWriteSkipCount()).append("\n");
     sb.append("exitStatus").append(stepExecution.getExitStatus()).append("\n");
     sb.append("terminateOnly").append(stepExecution.isTerminateOnly()).append("\n");
     sb.append("filterCount").append(stepExecution.getFilterCount()).append("\n");
     sb.append("failureExceptions").append(stepExecution.getFailureExceptions()).append("\n");
     *
     * @return String formatted table of current Step Executions in the database
     */
    public String logStepExecutions() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n").append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center("STEP EXECUTIONS (DB)", 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_TWO_FORMAT, StringUtils.center(COLUMN, 22), StringUtils.center(RESULT, 32))).append("\n");
        sb.append(LINE).append("\n");

        return jdbcTemplate.query(STEP_EXECUTIONS, rs -> {
            while (rs.next()) {

                sb.append(printRow(STEP_EXECUTION_ID, rs.getString(1)));

                sb.append( printRow(JOB_EXECUTION_ID, rs.getString(2)));
                sb.append( printRow(JOB_NAME, rs.getString(3)));
                sb.append( printRow(STEP_NAME, rs.getString(4)));

                sb.append( printRow(START_TIME, rs.getString(5)));
                sb.append( printRow(END_TIME, rs.getString(6)));
                sb.append( printRow(COMMIT_COUNT, rs.getString(7)));
                sb.append( printRow(READ_COUNT, rs.getString(8)));
                sb.append( printRow(WRITE_COUNT, rs.getString(9)));

                sb.append( printRow(STATUS, rs.getString(10)));
                sb.append( printRow(EXIT_CODE, rs.getString(11)));
                sb.append( printRow(LAST_UPDATED, rs.getString(11)));

                sb.append(LINE).append("\n");
            }

            sb.append("\n");
            return sb.toString();
        });
    }

    public String logStepExecutions(StepExecution stepExecution) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n").append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center("STEP EXECUTION", 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(COLUMN, 22), StringUtils.center(RESULT, 32))).append("\n");
        sb.append(LINE).append("\n");

        sb.append( printRow(JOB_EXECUTION_ID, ""+stepExecution.getJobExecutionId()) );
        sb.append( printRow(JOB_NAME, ""+stepExecution.getJobExecution().getJobConfigurationName()) );

        sb.append( printRow(STEP_NAME, stepExecution.getStepName()) );

        sb.append( printRow(STATUS, ""+stepExecution.getStatus()));
        sb.append( printRow(READ_COUNT, ""+stepExecution.getReadCount()));
        sb.append( printRow(WRITE_COUNT, ""+stepExecution.getWriteCount()));
        sb.append( printRow(COMMIT_COUNT, ""+stepExecution.getCommitCount()));
        sb.append( printRow("ROLLBACK_COUNT", ""+stepExecution.getRollbackCount()));
        sb.append( printRow("READ_SKIP_COUNT", ""+stepExecution.getReadSkipCount()));
        sb.append( printRow("PROCESS_SKIP_COUNT", ""+stepExecution.getProcessSkipCount()));
        sb.append( printRow("WRITE_SKIP_COUNT", ""+stepExecution.getWriteSkipCount()));
        sb.append( printRow(START_TIME, ""+stepExecution.getStartTime()));
        sb.append( printRow(END_TIME, ""+stepExecution.getEndTime()));
        sb.append( printRow(LAST_UPDATED, ""+stepExecution.getLastUpdated()));
        sb.append( printRow("TERMINATE_ONLY", ""+stepExecution.isTerminateOnly()));
        sb.append( printRow("FILTER_COUNT", ""+stepExecution.getFilterCount()));
        sb.append( printRow("FAILURE_EXCEPTIONS", ""+stepExecution.getFailureExceptions()));



        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(JOB_EXECUTION_PARAMS, 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(""+stepExecution.getJobParameters(), 55))).append("\n");

//        sb.append( printRow("*fileName ???", ""+stepExecution.getExecutionContext().getString("fileName")));

        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(EXIT_STATUS, 55))).append("\n");
        sb.append(LINE).append("\n");
        sb.append(String.format(ROW_FORMAT, StringUtils.center(""+stepExecution.getExitStatus(), 55))).append("\n");


        sb.append(LINE).append("\n");
        sb.append("\n\n");

        return sb.toString();
    }





    private String printRow(String k, String v) {

        return (String.format(COLUMN_FORMAT, k)) + (String.format(RESULT_FORMAT, v)) +"\n";

    }

    public static String consoleLine(char lineChar){
        return consoleLine(55, lineChar);
    }

    public static String consoleLine(int length, char lineChar){
        return "+" + new String(new char[length]).replace('\0', lineChar) + "+";
    }

} // The End...
